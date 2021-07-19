(function($) {
    $.widget("jazz.gridtreepanel", $.jazz.gridpanel, {

        options: /** @lends jazz.gridcolumn# */ {
            /**
             * @desc 组件类型
             * @type 'String'
             */
            vtype: "gridtree",
            /**
             * @type String
             * @desc gridcolumn组件标识名称
             * @default null
             */
            name: null,
            /**
             * @type String
             * @desc 数据请求地址url
             * @default null
             */
            dataurl: null,
            /**
             * @type String
             * @desc 父级节点key
             * @default pId
             */
            pidkey: "pId",
            /**
             * @type String
             * @desc 本级节点key
             * @default pId
             */
            idkey: "id",
            /**
             * @type String
             * @desc 数据中子级节点的key
             * @default pId
             */
            childkey: "children",
            /**
             * @type String
             * @desc 数据标示节点是否是父级节点的key
             * @default isParent
             */
            isparentkey: "isParent",
            /**
             * @type String
             * @desc 用于标示数据等级的key
             * @default isParent
             */
            levelkey: "level",
            /**
             * @type String
             * @desc 用于标示数据节点是否打开
             * @default isopen
             */
            isopenkey: "isopen",
            /**
             * @type String
             * @desc 变现出tree形式的列name
             * @default null
             */
            treecolumnname: null,
            /**
             * @type String
             * @desc gridtree分页类型 "0"是以总结点数分， "1"是以根节点数分
             * @default "0"
             */
            paginationtype: "1"
        },
        /**
         * @desc 创建gridtreepanel组件
         * @private
         */
        _create: function() {
            this._super();

            this.element.addClass('jazz-gridtreepanel');
        },
        /**
         * @desc 初始化gridtreepanel组件
         * @private
         */
        _init: function() {
            /**
             * 最为核心的树形数据,他就是GridTree的核心
             * 1.一直保存
             * 2.全后台数据一致
             * 3.与grid展现的一致
             *
             * id十分重要！！！！！
             * 通过id可以吧treeData，dataMap，relationshipMap相互关联！！！！
             * treeData中的id可以找到对应的dataMap，relationshipMap中自己的信息
             * */
            this.treeData = null;
            /**
             * 请求回来的原始数据，没用就保存了下，可以考虑废了
             * */
            this.orgData = null;
            /**
             * 保存了id与treeData对象的映射关系
             * 通过id可以找到treeData对应的对象
             * */
            this.dataMap = {};
            /**
             * 保存了直系对象见的关系，用id来保存关系
             * 通过Id可以找到自己父级的直系对象
             * */
            this.relationshipMap = {};
            /**
             * 保存了每一页中处于选中状态的节点的id集合
             * {page1:[1,2,4,6],page2:[7,8,0]}
             */
            this.checkedNodeIds = {};
            /**
             * 保存了每一页中处于展开子节点的id的集合
             * {page1:[1,2,4,6],page2:[7,8,0]}
             */
            this.openNodeIds = {};
            this._super();
        },
        /**
         * @desc gridtreepanel初始化创建、查询、排序、翻页等获取数据后渲染表格卡片
         * @param {loadDataType} 获取渲染数据的逻辑情景，如：“init”
         * @param {responseText} ajax获取返回数据
         * @param {requestdatacomplete} 渲染数据回调函数
         * @param {isRebindPaginator} 是否再次绑定分页条（初始化时绑定，翻页时不再绑定）
         * @private
         * @example this._successLoadData("init",responseText,function(data){...},true);
         */
        _successLoadData: function(loadDataType, responseText, requestdatacomplete, isRebindPaginator) {

            this.dataMap = {};
            this.relationshipMap = {};

            var that = this;
            if (responseText && typeof(responseText) == 'object') {
                var dataObj = responseText['data'];
                if (dataObj) {
                    //如果没有分页条组件就没必要缓存分页信息
                    if (that.gpaginator) {
                        that.paginationInfo['page'] = dataObj["page"] || 1;
                        that.paginationInfo['pagerows'] = dataObj["pagerows"] || 10;
                        that.paginationInfo['totalrows'] = dataObj["totalrows"] || 0;
                        that._updatePageInfo(that.paginationInfo);
                    }
                    //缓存gridTreePanel原始数组数据和二位数数据
                    that.orgData = dataObj["rows"];
                    that.treeData = that._transformTozTreeFormat(dataObj["rows"]);
                    that.treeData = that._markTreeDataLevelAndRelationship(that.treeData, 0, '');

                    if (that.treeData) {

                        if (loadDataType == "init") {

                            this._renderGridTreepanelData("init");

                        } else if (loadDataType == "load") {

                            var table = this.gtable.data("gridtable");
                            //先删除行
                            table.removeTreeRows({
                                length: table.getAllTreeRows().length
                            });
                            //补全待生成行的数据
                            this._fixPageNodesData(function() {
                                //数据加载完之后，打开相对应的父级节点
                                that._fixOpenNodeParent();
                                //生成行
                                that._renderGridTreepanelData("load");
                                //最后补全行信息（选中信息）
                                that._fixPageCheckedNodes();
                            });

                        }

                    }

                }
            } //if
            //隐藏loading组件
            try {
                that.element.find(".jazz-pagearea").loading("hide");
            } catch (e) {
                jazz.log(e);
            }
        },
        /**
         * @desc gridtreepanel 将数据变为gridtable可识别数据（用于addTreeRow方法调用）
         * @param {runType} 获取渲染数据的逻辑情景，如：“init”，“load”
         * @private
         */
        _renderGridTreepanelData: function(runType) {
            if (this.gpaginator) {
                var type = this.options.paginationtype,
                    page = this.paginationInfo['page'],
                    pagerows = this.paginationInfo['pagerows'],
                    totalrows = this.paginationInfo['totalrows'];

                //每一个分页都是单独的数据不缓存所以起始位置永远是0到pagerows
                var start = 0,
                    finish = pagerows - 1;

                if (runType == "load") {
                    //如果是翻页则要获得全部子节点数据并打开
                    var rowsData = this._getGridAllOpenTreeNodesData();
                } else {
                    //用Index取数据是为了防止以后变成缓存全部分页
                    //start = (page - 1) * pagerows,
                    //finish = (page * pagerows) - 1;
                    var rowsData = this._getGridRootTreeNodesDataByIndex(start, finish);
                }
            } else {
                //没有分页栏就直接取得全部根节点
                var rowsData = this._getGridRootTreeNodesData();
            }

            //将有序的id数据变成真正的data数据
            var arr = this._getDataById(rowsData);

            if (this.gtable) {
                var gridtable = this.gtable.data('gridtable');
                if (gridtable) {
                    //先把options参数传给gridtable
                    gridtable.options.idkey = this.options.idkey;
                    gridtable.options.pidkey = this.options.pidkey;
                    gridtable.options.childkey = this.options.childkey;
                    gridtable.options.treecolumnname = this.options.treecolumnname;
                    gridtable.options.isparentkey = this.options.isparentkey;
                    gridtable.options.levelkey = this.options.levelkey;
                    gridtable.options.isopenkey = this.options.isopenkey;
                    //添加行
                    gridtable.addTreeRow(arr);
                } else {
                    loopReloadData(this.gtable, 'gridtable', 0);
                }
            } else {
                //没找到gtable就报错
            }
        },
        // /**
        //  * @desc 用于获得树数据固定字段
        //  * @param {start} int 起始行号（最小值为0）
        //  * @param {finish} int 截止行号
        //  * @param {type} 分页类型 "0"是以总结点数分， "1"是以根节点数分
        //  * @return {ids} Array 有序的id的数组
        //  */
        // _getGridTreeNodesDataByIndex: function(start, finish, type) {
        //     var ids = [],
        //         id = this.options.idkey,
        //         root_len = this.treeData.length - 1,
        //         childkey = this.options.childkey;

        //     if (type == "0") {
        //         //每次节点总数
        //         var NO = 0,
        //             arrs,
        //             arr;
        //         //递归遍历对应数组
        //         (function(arrs) {
        //             for (var i = 0, l = arrs.length; i < l; i++) {
        //                 arr = arrs[i];
        //                 //根据边界判断时候放入id
        //                 if (start <= NO && NO <= finish) {
        //                     ids.push(arr[id]);
        //                 }
        //                 NO++;

        //                 //在判断是否有子节点
        //                 if (arr[childkey] && arr[childkey].length) {
        //                     arguments.callee(arr[childkey]);
        //                 }
        //             }
        //         })(this.treeData);

        //     } else if (type == "1") {
        //         //效验边界
        //         if (root_len <= start) {
        //             return ids;
        //         } else if (root_len <= finish) {
        //             finish = root_len;
        //         }
        //         //取得边界内的m=id集合
        //         for (var i = start, j = finish; i <= j; i++) {
        //             ids.push(this.treeData[i][id]);
        //         }
        //     }
        //     return ids;
        // },
        /**
         * @desc 根据index获得树结构的根节点ids
         * @param {start} int 起始行号（最小值为0）
         * @param {finish} int 截止行号
         * @return {ids} Array 有序的id的数组
         */
        _getGridRootTreeNodesDataByIndex: function(start, finish) {
            //finish传进来的是index，需要改成length，所以要加1
            finish += 1;
            var ids = [],
                id = this.options.idkey,
                j = finish > this.treeData.length ? this.treeData.length : finish;

            for (var i = start; i < j; i++) {
                ids.push(this.treeData[i][id]);
            }

            return ids;
        },
        /**
         * @desc 用于获得树结构的全部根节点ids
         * @return {ids} Array 有序的id的数组
         */
        _getGridRootTreeNodesData: function() {
            var ids = [],
                id = this.options.idkey;

            for (var i = 0, j = this.treeData.length; i < j; i++) {
                ids.push(this.treeData[i][id]);
            }

            return ids;
        },
        /**
         * @desc 用于获得树结构的全部ids
         * @return {ids} Array 有序的id的数组
         */
        _getGridAllTreeNodesData: function() {
            var ids = [],
                id = this.options.idkey,
                root_len = this.treeData.length,
                childkey = this.options.childkey;


            var arrs,
                arr;
            //递归遍历对应数组
            (function(arrs) {
                //                    arrs = arguments[0];
                for (var i = 0, l = arrs.length; i < l; i++) {
                    arr = arrs[i];
                    //根据边界判断时候放入id
                    ids.push(arr[id]);

                    //在判断是否有子节点
                    if (arr[childkey] && arr[childkey].length) {
                        arguments.callee(arr[childkey]);
                    }
                }
            })(this.treeData);

            return ids;
        },
        /**
         * @desc 用于获得树结构的全部处于打开状态ids
         * @return {ids} Array id的数组
         */
        _getGridAllOpenTreeNodesData: function() {
            var ids = [],
                id = this.options.idkey,
                root_len = this.treeData.length,
                childkey = this.options.childkey,
                isopenkey = this.options.isopenkey,
                levelkey = this.options.levelkey;

            var arrs,
                arr,
                children;
            //递归遍历对应数组
            (function(arrs) {
                for (var i = 0, l = arrs.length; i < l; i++) {
                    arr = arrs[i];
                    ids.push(arr[id]);
                    //如果节点是展开状态就继续遍历其子级
                    if (arr[isopenkey] == true || arr[isopenkey] == "true") {
                        arguments.callee(arr[childkey]);
                    }
                }
            })(this.treeData);

            return ids;
        },
        /**
         * @desc 由id数组转化为ids数据数据
         * @return {ids} Array ids数据的数组
         */
        _getDataById: function(arr) {
            var ids = [];
            for (var i = 0, j = arr.length; i < j; i++) {
                ids.push(this.dataMap[arr[i]]);
            }
            return ids;
        },
        /**
         * @desc 将二维数组数据转化成有层级的树形数据对象
         * @param {sNodes} 原始的二维数组数据
         * @return  Array 树形数组数据
         */
        _transformTozTreeFormat: function(sNodes) {
            var i, l,
                key = this.options.idkey,
                parentKey = this.options.pidkey,
                childkey = this.options.childkey;
            if (!key || key == "" || !sNodes) return [];

            if (jazz.isArray(sNodes)) {
                var r = [];
                var tmpMap = [];
                for (i = 0, l = sNodes.length; i < l; i++) {
                    tmpMap[sNodes[i][key]] = sNodes[i];
                }
                for (i = 0, l = sNodes.length; i < l; i++) {
                    if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
                        if (!tmpMap[sNodes[i][parentKey]][childkey])
                            tmpMap[sNodes[i][parentKey]][childkey] = [];
                        tmpMap[sNodes[i][parentKey]][childkey].push(sNodes[i]);
                    } else {
                        r.push(sNodes[i]);
                    }
                }
                return r;
            } else {
                return [sNodes];
            }
        },
        /**
         * @desc 标示各个结点的层级等级和父级关系链，同时维护了dataMap和relationshipMap
         * @param {data} 同级节点数组
         * @param {level} 预设的层级等级
         * @param {parentStep} 预设的父级关系链
         * @return {data} Array 树形数组数据
         */
        _markTreeDataLevelAndRelationship: function(data, level, parentStep) {
            var lv = this.options.levelkey,
                temp, id, pIdLink;
            for (var i = 0, j = data.length; i < j; i++) {
                //确定level，level用于决定队形树那列文字左边距的大小
                data[i][lv] = level;
                //id是唯一的
                id = data[i][this.options.idkey];
                temp = data[i][this.options.childkey];
                //关联id和对象
                this.dataMap[id.toString()] = data[i];
                //关联对象的直系关系
                pIdLink = parentStep + "-" + id.toString();
                this.relationshipMap[id.toString()] = pIdLink;
                if (temp) {
                    data[i][this.options.isparentkey] = true;
                    data[i][this.options.isopenkey] = data[i][this.options.isopenkey] || false;
                    temp = this._markTreeDataLevelAndRelationship(temp, level + 1, pIdLink);
                }
            }
            return data;
        },
        /**
         * @desc 打开节点时候的操作，没有分页条的话，全部节点已经在table上了所以不用删除其他节点
         * @param {info} rebuild所需要的信息
         */
        _openNodesWithoutPaginator: function(info) {
            var id = info.id,
                index = info.lineIndex,
                childrenkey = this.options.childkey,
                that = this;

            if (!this.dataMap[id][childrenkey] || !this.dataMap[id][childrenkey].length) {
                this.loadChildrenData(info.id, function(data) {
                    that.addNodes(info.id, data.data.rows);
                    that._openNodesWithoutPaginator(info);
                });
                return;
            }
            this.dataMap[id][this.options.isopenkey] = true;
            //在对应位置添加行
            this.gtable.data('gridtable').addTreeRow(this.dataMap[id][childrenkey], index + 1);

        },
        /**
         * @desc 打开节点时候的操作，有分页条的话，要看每页的节点数能添加多少，在添加后的多余的行数删掉
         * @param {info} rebuild所需要的信息
         */
        _openNodesWithPaginator: function(info) {

            var that = this,
                id = info.id,
                index = info.lineIndex,
                page = this.paginationInfo['page'],
                pagerows = this.paginationInfo['pagerows'],
                currentrows = this.gtable.data('gridtable').getAllTreeRows().length;

            var children = this.dataMap[id].children;

            if (!children || !children.length) {
                this.loadChildrenData(id, function(data) {
                    that.addNodes(id, data.data.rows);
                    that._openNodesWithPaginator(info);
                });
                return;
            }

            var children_len = children.length;

            //页面可添加的行数计算
            var leftRows = pagerows - index - 1;
            if (leftRows < children_len) {
                //如果children数量多于index以下的总行数，则表示只能生成一部分
                //删除多余的children对象
                children = children.splice(index - 1, children.length - index);
                children_len = children.length;
            }

            this.dataMap[id][this.options.isopenkey] = true;
            //在对应位置添加行
            this.gtable.data('gridtable').addTreeRow(children, index + 1);

            //删除行操作
            this.gtable.data('gridtable').removeTreeRows({
                length: (children_len + currentrows - pagerows)
            });
        },
        /**
         * @desc 关闭节点时候的操作，没有分页条的话，全部节点已经在table上了所以不用再打开新的节点
         * @param {info} rebuild所需要的信息
         */
        _closeNodesWithoutPaginator: function(info) {
            var id = info.id,
                index = info.lineIndex;

            var children_len = this.gtable.data('gridtable').getChildrenRows(index).length;

            this.dataMap[id][this.options.isopenkey] = false;
            //删除行操作
            this.gtable.data('gridtable').removeTreeRows({
                start: index + 1,
                length: children_len
            });

        },
        /**
         * @desc 关闭节点时候的操作
         * @param {info} rebuild所需要的信息
         */
        _closeNodesWithPaginator: function(info) {
            var index = info.lineIndex,
                lv = this.options.levelkey,
                id = info.id,
                //用于控制跳出全部循环，在应添加行数大于实际剩余行数时候出现
                isBreak = false,
                page = this.paginationInfo['page'],
                pagerows = this.paginationInfo['pagerows'],
                totalrows = this.paginationInfo['totalrows'];

            //1.算出来点击行下面剩多少行，直接删了

            var leftRows = pagerows - index - 1;
            this.gtable.data('gridtable').removeTreeRows({
                start: index + 1,
                length: leftRows
            });

            //2.找点击行的平级和平级以上的节点填充

            //新展开的行应该取同级节点弟级节点，再取父级的弟级节点，再取父级的父级的弟级节点，以此类推
            var ids = [],
                node = this.dataMap[id],
                tempNode = node,
                nodeType = "brother";

            //找到下一个
            node = this.getNextNode(node);
            while (ids.length != leftRows && !isBreak) {
                while (!(node && nodeType == "brother")) {
                    if (nodeType == "brother") {
                        if (tempNode[lv] == 0) {
                            //如果找的已经是兄弟节点了，但是没有找到，且自身就是根节点
                            isBreak = true;
                            break;
                        } else {
                            //如果找到不到兄弟节点则去找父级（下次遍历的时候找父级的兄弟节点）
                            //且只要不是根节点都会找到父级
                            node = this.getParentNode(tempNode);
                            nodeType = "parent";
                        } // if
                    } else if (nodeType == "parent") {
                        //如果本身就是parent则需要去找父级的兄弟节点
                        //这个节点在下一次遍历时候如果依旧为未找到的话就再向上一层找父级
                        tempNode = node;
                        node = this.getNextNode(node);
                        nodeType = "brother";
                    } // if
                } // while
                //不用判断node是否存在，经过上面的while，node要不已经赋值要不就是到达数据结尾直径break跳出
                if (!isBreak) {
                    //打入当前的
                    ids.push(node);
                    //找到下一个
                    tempNode = node;
                    node = this.getNextNode(node);
                }
            } // while

            this.dataMap[id][this.options.isopenkey] = false;
            //生成新行
            this.gtable.data('gridtable').addTreeRow(ids);

        },
        /**
         * @desc 修改分页条信息
         * @param {page} 当前页号
         * @param {pagerows} 每页页数
         * @param {totalrows} 总页数
         */
        _updatePageInfo: function(page, pagerows, totalrows) {
            if (typeof page == "object") {
                if (this.gpaginator) {
                    this.gpaginator.data('paginator').updatePage({
                        "page": page.page,
                        "pagerows": page.pagerows,
                        "totalrecords": page.totalrows
                    });
                }
            } else {
                if (this.gpaginator) {
                    this.gpaginator.data('paginator').updatePage({
                        "page": page,
                        "pagerows": pagerows,
                        "totalrecords": totalrows
                    });
                }
            }
        },
        /**
         * @desc 根据当前页号还原页面中选中的节点
         * @private
         */
        _fixPageCheckedNodes: function() {
            var pageId = this.paginationInfo['page'],
                table = this.gtable.data("gridtable"),
                id,
                node;
            var checkedNodeIds = this.checkedNodeIds["page" + pageId] || [],
                trs = table.getAllTreeRows();

            for (var i = 0, j = checkedNodeIds.length; i < j; i++) {
                id = checkedNodeIds[i];
                node = trs.find("#" + id);
                if (node.length) {
                    node.parents("tr").find(".jazz-grid-cell-box").find("input").attr("checked", "checked");
                }
            }
        },
        /**
         * @desc 根据当前页号还原页面信息
         * @private
         */
        _fixPageNodesData: function(callback) {
            this.tempNodesLength = 0;
            var pageId = this.paginationInfo['page'],
                that = this;
            var openNodeIds = this.openNodeIds["page" + pageId];

            //如果openNodeIds含有本页的信息则需要先处理nodes数据，如果没有则直接调用回调
            if (openNodeIds && openNodeIds.length) {
                //遍历节点把所有需要展开的节点全部打开
                for (var i = 0; i < openNodeIds.length; i++) {
                    //遍历所有节点，并把所有节点的子级节点全部取到
                    var id = openNodeIds[i];
                    if (this.dataMap[id] && this.dataMap[id][this.options.childkey] && this.dataMap[id][this.options.childkey].length) {
                        continue;
                    }
                    this.tempNodesLength++;
                    this.loadChildrenData(id, function(data) {
                        that._checkPageNodesRequest(data, callback);
                    });
                }
                //如果没有调用后台请求的节点存在则直接调用callback
                if (this.tempNodesLength == 0) {
                    if (callback && typeof callback == "function") {
                        callback();
                    }
                }
            } else {
                if (callback && typeof callback == "function") {
                    callback();
                }
            }
        },

        /**
         * @desc 把展开节点的父级节点全部展开
         * @private
         */
        _fixOpenNodeParent: function() {
            this.tempNodesLength = 0;
            var pageId = this.paginationInfo['page'],
                that = this,
                isopenkey = this.options.isopenkey,
                pNode,
                links,
                id;
            var openNodeIds = this.openNodeIds["page" + pageId] || [];

            //遍历节点把所有需要展开的节点全部打开
            for (var i = 0; i < openNodeIds.length; i++) {
                id = openNodeIds[i];
                links = this.relationshipMap[id].split("-");
                for (var j = 1, k = links.length; j < k; j++) {
                    this.dataMap[links[j]][isopenkey] = true;
                }
            }
        },
        /**
         * @desc 处理取到的自己数据，在得知全部数据回来的时候
         * @private
         */
        _checkPageNodesRequest: function(data, callback) {
            var pidkey = this.options.pidkey,
                isopen = this.options.isopenkey;
            this.tempNodesData = this.tempNodesData || [];
            data = data.data.rows;
            this.tempNodesData.push({
                pId: data[0][pidkey],
                data: data
            });
            //如果请求全部回来了就要开始网dataMap里面方数据了
            if (this.tempNodesData.length == this.tempNodesLength) {
                for (var i = 0; i < this.tempNodesData.length; i++) {
                    pid = this.tempNodesData[i].pId;
                    if (this.dataMap[pid]) {
                        this.addNodes(pid, this.tempNodesData[i].data);
                        this.dataMap[pid][isopen] = true;
                        this.tempNodesData.splice(i, 1);
                        i = -1;
                    }
                }
                if (callback && typeof callback == "function") {
                    callback();
                }
            }
        },
        /**
         * @desc 保存当页的节点信息，包括哪些节点被选中，哪些节点是展开状态，方便还原该页
         * @private
         */
        _savePageNodesInfo: function() {
            var pageId = this.paginationInfo['page'],
                checkedNodeIds = [],
                openNodeIds = [];

            var trs = this.gtable.data("gridtable").getAllTreeRows(),
                checkbox,
                node,
                nextNode;

            //遍历节点
            $.each(trs, function(i, tr) {
                checkbox = $(tr).find(".jazz-grid-cell-box").find("input");
                node = $(tr).find(".jazz-grid-tree-cell");
                nextNode = $(tr).next().find(".jazz-grid-tree-cell");
                //判断是否选中
                if (checkbox.attr("checked") == "checked") {
                    checkedNodeIds.push(node.attr("id"));
                }

                //判断是否节点展开
                if (nextNode.length && node.attr("id") == nextNode.attr("pid")) {
                    openNodeIds.push(node.attr("id"));
                }
            });

            this.checkedNodeIds["page" + pageId] = checkedNodeIds;
            this.openNodeIds["page" + pageId] = openNodeIds;
        },
        /**
         * @desc 根据paginationinfo的页面信息去请求，可执行翻页和刷新当页操作
         * @private
         */
        requestPageData: function() {

            var params = {
                url: this.options.dataurl + "?page=" + this.paginationInfo['page'],
                params: {},
                pageparams: this.paginationInfo,
                showloading: false,
                callback: function(responseText, $this) {
                    $this._successLoadData("load", responseText, null, null);
                }
            };
            $.DataAdapter.submit(params, this);

        },
        /**
         * @desc 根据节点找到父级节点
         */
        getParentNode: function(id) {
            if (typeof id == "object") {
                id = id[this.options.idkey];
            }
            if (id === undefined || id === null) {
                return;
            }
            //先获得Id对应的关系链 肯定是-xx-xx-xx-Id最后一个肯定是本身
            var idLinks = this.relationshipMap[id].split("-");
            //所以切分关系链之后数组的倒数第二个肯定就是parent
            //倒数第一个是本身 倒数第二个是父级
            var pId = idLinks[idLinks.length - 2];
            if (pId == "") {
                return {
                    children: this.treeData
                };
            } else {
                return this.dataMap[pId];
            }
        },
        /**
         * @desc 根据节点找到平级中上一个节点
         */
        getPrevNode: function(id) {
            if (typeof id == "object") {
                id = id[this.options.idkey];
            }
            if (id === undefined || id === null) {
                return;
            }
            //先找父级
            var parentNode = this.getParentNode(id),
                prev = null;
            //通过父级找到全部的子级
            var children = parentNode.children;
            for (var i = 0, j = children.length; i < j; i++) {
                //遍历子级到找到Id前面的那个对象，没有的话就返回[]
                if (children[i][this.options.idkey] == id) {
                    prev = i == 0 ? null : children[i - 1];
                    break;
                }
            }
            return prev;
        },
        /**
         * @desc 根据节点找到平级中下一个节点
         */
        getNextNode: function(id) {
            if (typeof id == "object") {
                id = id[this.options.idkey];
            }
            if (id === undefined || id === null) {
                return;
            }
            //先找父级
            var parentNode = this.getParentNode(id),
                next = null;
            //通过父级找到全部的子级
            var children = parentNode.children;
            for (var i = 0, j = children.length; i < j; i++) {
                //遍历子级到找到Id前面的那个对象，没有的话就返回[]
                if (children[i][this.options.idkey] == id) {
                    next = i == (j + 1) ? null : children[i + 1];
                    break;
                }
            }
            return next;
        },
        /**
         * @desc 根据id获得该节点下面的子节点
         */
        getChildrenNodes: function(id) {
            if (typeof id == "object") {
                id = id[this.options.idkey];
            }
            if (id === undefined || id === null) {
                return;
            }
            //通过datamap找到对应的data对象
            var node = this.dataMap[id];
            return node.children || [];
        },
        /**
         * @desc 往pid节点上增加一个节点
         */
        addNode: function(pId, node) {
            this.addNodes(pId, [node]);
        },
        /**
         * @desc 往pid节点上增加节点
         */
        addNodes: function(pId, nodes) {

            if (!nodes || !jazz.isArray(nodes)) {
                return;
            }

            var idkey = this.options.idkey,
                lvkey = this.options.levelkey,
                parentLink = this.relationshipMap[pId],
                childkey = this.options.childkey,
                parentNode,
                id,
                nodeLevel;

            //p
            if (pId == 0) {
                nodeLevel = 0;
            } else {
                parentNode = this.dataMap[pId];
                nodeLevel = parentNode[lvkey] + 1;
            }

            for (var i = 0, j = nodes.length; i < j; i++) {
                id = nodes[i][idkey];
                //维护dataMap
                this.dataMap[id] = nodes[i];
                //维护relationshipMap
                this.relationshipMap[id] = parentLink + "-" + id;
                //添加level信息
                nodes[i][lvkey] = nodeLevel;
            }

            //添加children
            if (pId == 0) {
                this.treeData.split(this.treeData.length - 1, null, nodes);
            } else if (parentNode[childkey] && parentNode[childkey].length) {
                this.dataMap[pId][childkey].split(parentNode.children.length - 1, null, nodes);
            } else {
                this.dataMap[pId][childkey] = nodes;
            }
        },
        loadChildrenData: function(pId, callback) {
            var that = this;
            if (this.options.dataurl) {
                //添加加载缓冲组件
                this.element.find(".jazz-pagearea").loading();

                var params = {
                    url: this.options.dataurl + "?pId=" + pId,
                    params: {
                        pId: pId
                    },
                    pageparams: that.paginationInfo,
                    showloading: false,
                    callback: callback
                };
                $.DataAdapter.submit(params, that);

                try {
                    that.element.find(".jazz-pagearea").loading("hide");
                } catch (e) {
                    jazz.log(e);
                }
            }
        },

        /**
         * @desc 重塑table中的rows
         * @param {type} rebuild的类型
         * type == 0 标示打开或者关闭节点
         * @param {info} rebuild所需要的信息
         * @exmp
         */
        rebuildTreeTable: function(type, info) {
            if (type == 0) {
                if (this.gpaginator && this.options.paginationtype == "0") {
                    //有分页条且分页以总结点数分页的
                    if (info.isOpen) {
                        this._closeNodesWithPaginator(info);
                    } else {
                        this._openNodesWithPaginator(info);
                    } // if
                } else {
                    //不分页或者以根节点分页的
                    if (info.isOpen) {
                        this._closeNodesWithoutPaginator(info);
                    } else {
                        this._openNodesWithoutPaginator(info);
                    } // if
                } // if

            }
        },
        /**
         * @desc gridpanel绑定paginator分页点击响应事件(该函数在jazz.gpaginator组件中调用)
         * @param {page} 页码
         * @param {pagerows} 当前页显示条数
         * @private
         * @example this.bindPaginatorClickEvent(page,pagerows);
         */
        bindPaginatorClickEvent: function(page, pagerows) {
            this._savePageNodesInfo();
            this.paginationInfo['page'] = page;
            this.paginationInfo['pagerows'] = pagerows;
            this.requestPageData();
        }
    });

})(jQuery);
