package com.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.report.bean.FileDown;
import com.report.dao.RedisDao;
import com.report.dao.impl.FileDaoImpl;
import com.report.service.Data01Service;
import com.report.service.Data02Service;
import com.report.service.Data03Service;
import com.report.service.Data04Service;
import com.report.service.Data05Service;
import com.report.task.ReportCacheBusTask;
import com.report.util.DataGrid;
import com.report.util.PageInfo;
import com.report.util.StrUtil;
import com.report.util.UUIDUtils;
import com.report.util.excell.Data01ExcelUtil;
import com.report.util.excell.Data02ExcelUtil;
import com.report.util.excell.Data03ExcelUtil;
import com.report.util.excell.Data04ExcelUtil;
import com.report.util.excell.Data05ExcelUtil;
import com.report.util.excell.TaiWanExcelUtil;

@Controller
public class IndexController {

	
	@Autowired
	private Data01Service data01Service;
	@Autowired
	private Data02Service data02Service;
	@Autowired
	private Data03Service data03Service;
	@Autowired
	private Data04Service data04Service;
	@Autowired
	private Data05Service data05Service;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private FileDaoImpl fileDaoImpl;
	
	
	@Autowired
	private ReportCacheBusTask reportCacheBusTask;
	
	@RequestMapping(value={"/","/index"})
	public String doIndex() {
		return "index";
	}
	
	
	
	
	//https://www.cnblogs.com/commissar-Xia/p/7759484.html
	/*
	 * *：通配任意多个字符

		?：通配单个字符
		
		[]：通配括号内的某一个字符
	 */
	@ResponseBody
	@RequestMapping("keys")
	public List   getKeys(String pattern) {
		Set<String> sets = redisDao.keys("*" + pattern + "*");
		List<String> keys = new ArrayList<String>();
		for (String string : sets) {
			keys.add(string);
		}
		
	  redisDao.delete(keys);
	  return keys;
	}
	
	
	//https://www.xuebuyuan.com/2965209.html
	//https://q.cnblogs.com/q/85246/
	//https://blog.csdn.net/qq_33801617/article/details/78113187
	@ResponseBody
	@RequestMapping(value="/list")
	public DataGrid  getItemsListForDown(String time,PageInfo dgparam) {
		System.out.println(dgparam.toString());
		List list = fileDaoImpl.getFileToDown(time,dgparam);
		Integer total = fileDaoImpl.getTotal(time,dgparam);
		DataGrid dg = new DataGrid();
		dg.setRows(list);
		dg.setTotal(total);
		return dg;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/delItem")
	public Map  delItem(String id,String startTime) {
		String fileOutPath = "c://yuan_ye_excell//" + startTime + "//";
		HashMap map = new HashMap();
		List params = new ArrayList<>();
	
		System.out.println(id);
		if(!StrUtil.isEmpty(id)) {
			if(id.contains(",")) {
				  String [] judgeId = id.split(",");
				  for (String string : judgeId) {
					  params.add(string);
				}
			}else {
				params.add(id);
			}
			try {
				fileDaoImpl.delete(params);
				map.put("code", "删除成功");
					delAllFile(fileOutPath); // 删除完里面所有内容
					java.io.File myFilePath = new java.io.File(fileOutPath);
					myFilePath.delete(); // 删除空文件夹
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "系统出现异常。");
			}
			
		}else {
			map.put("code", "要删除的ID数据不存在");
		}
		return map;
	}
	

	@RequestMapping("query")
	@ResponseBody
	public Map query(String startTime,String isDown,HttpServletResponse response) throws ParseException, IOException {
		Map map = new HashMap();
		
	
		if(startTime==null){
			map.put("code", "时间不能为空");
			return map;
		}
		
		
	
		
		/*List list01 = data01Service.getList(startTime);
		List list01_02 = data01Service.getList2(startTime);
		
		
		
		
		List list02SumJidu = data02Service.getSumJiDu(startTime);
		List list02SumAll = data02Service.getSumAll(startTime);

		List list02NeiZiJiDu = data02Service.getNeiZiJiDu(startTime);
		List list02NeiZiAll = data02Service.getNeiZiAll(startTime);

		List list02SiYinJiDu = data02Service.getSiYinJiDu(startTime);
		List list02SiYinAll = data02Service.getSiYinAll(startTime);

		List list02WaiZiJiDu = data02Service.getWaiZiJiDu(startTime);
		List list02WaiZiAll = data02Service.getWaiZiAll(startTime);
		
		
		
		List list03BenQi = data03Service.list2_BenQi(startTime);
		List list03QuNianBenQi = data03Service.list2_QuNianBenQi(startTime);

		List list03JianNianLeiJi = data03Service.list1_JianNianLeiJi(startTime);
		List list03QuNianLeiJi = data03Service.list1_QuNianLeiJi(startTime);
		
	
		
		
		
		List list04BenQi = data04Service.list2BenQi(startTime);
		List list04QuNianBenQi = data04Service.list2_QuNianBenQi(startTime);

		List list04JinNianLeiJi = data04Service.list1_JinNianLeiJi(startTime);
		List list04QuNianLeiJi = data04Service.list1_QuNianLeiJi(startTime);
	
		
		List list05 = data05Service.getList(startTime);
		List list05LeiJi = data05Service.getList2(startTime);*/

		
		
		
	    List list01 = 	redisDao.get("list01"+startTime, List.class);
	    
	    if(list01 ==null){
	    	reportCacheBusTask.doBusForRedisByHand(startTime);
	    	map.put("code", "-1");
	    	map.put("msg", "没有在缓存库中，需要去重新采集");
	    	return map;
	    }
	    
	    
	   
		
		//System.out.println("===================>  生成成功");
		map.put("code", "生成成功");
		
		
		
		
		
		return map;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/down")
	public void down(HttpServletResponse response, String startTime) throws IOException, ParseException {

		List<String> fileNames = new ArrayList<String>();
		String fileOutPath = "c://yuan_ye_excell//" + startTime + "//";
		File file = new File(fileOutPath);
		// https://www.cnblogs.com/sunny3096/p/7813022.html
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String fileName = fileOutPath;

		List list01 = redisDao.get("list01" + startTime, List.class);
		List list01_02 = redisDao.get("list01_02" + startTime, List.class);
		List list02SumJidu = redisDao.get("list02SumJidu" + startTime, List.class);
		List list02SumAll = redisDao.get("list02SumAll" + startTime, List.class);
		List list02NeiZiJiDu = redisDao.get("list02NeiZiJiDu" + startTime, List.class);
		List list02NeiZiAll = redisDao.get("list02NeiZiAll" + startTime, List.class);
		List list02SiYinJiDu = redisDao.get("list02SiYinJiDu" + startTime, List.class);
		List list02SiYinAll = redisDao.get("list02SiYinAll" + startTime, List.class);
		List list02WaiZiJiDu = redisDao.get("list02WaiZiJiDu" + startTime, List.class);
		List list02WaiZiAll = redisDao.get("list02WaiZiAll" + startTime, List.class);
		List list03BenQi = redisDao.get("list03BenQi" + startTime, List.class);
		List list03QuNianBenQi = redisDao.get("list03QuNianBenQi" + startTime, List.class);
		List list03JianNianLeiJi = redisDao.get("list03JianNianLeiJi" + startTime, List.class);
		List list03QuNianLeiJi = redisDao.get("list03QuNianLeiJi" + startTime, List.class);
		List list04BenQi = redisDao.get("list04BenQi" + startTime, List.class);
		List list04QuNianBenQi = redisDao.get("list04QuNianBenQi" + startTime, List.class);
		List list04JinNianLeiJi = redisDao.get("list04JinNianLeiJi" + startTime, List.class);
		List list04QuNianLeiJi = redisDao.get("list04QuNianLeiJi" + startTime, List.class);
		List list05 = redisDao.get("list05" + startTime, List.class);
		List list05LeiJi = redisDao.get("list05LeiJi" + startTime, List.class);

		List listTaiWai = redisDao.get("listTaiWai" + startTime, List.class);

		String file01 = fileName + "1.深圳各区优势产业分布【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		String file02 = fileName + "2.深圳本期新登记企业前十名监管所【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		String file03 = fileName + "3.各辖区局当期新设立个体前三名的监管所【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		String file04 = fileName + "4.各辖区局当期新设立企业前三名的监管所【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		String file05 = fileName + "5.深圳本期新登记个体前十名监管所【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";
		String file06 = fileName + "台湾个体数据统计【" + startTime + "】" + UUIDUtils.getUUID32() + ".xls";

		//Data01ExcelUtil.createExcell(list01, list01_02, startTime, file01);
		Data02ExcelUtil.createExcell(list02SumJidu, list02SumAll, list02NeiZiJiDu, list02NeiZiAll, list02SiYinJiDu,list02SiYinAll, list02WaiZiJiDu, list02WaiZiAll, startTime, file02);
		Data03ExcelUtil.createExcell(list03BenQi, list03QuNianBenQi, list03JianNianLeiJi, list03QuNianLeiJi, startTime,	file03);
		Data04ExcelUtil.createExcell(list04BenQi, list04QuNianBenQi, list04JinNianLeiJi, list04QuNianLeiJi, startTime,	file04);
		Data05ExcelUtil.createExcell(list05, list05LeiJi, startTime, file05);
		//TaiWanExcelUtil.createExcell(listTaiWai, startTime, file06);

		//fileNames.add(file01);
		fileNames.add(file02);
		fileNames.add(file03);
		fileNames.add(file04);
		fileNames.add(file05);
		//fileNames.add(file06);

		
		
		
		
		//spring mvc 实现下载excell时，先打包
		// https://blog.csdn.net/qq_36675996/article/details/80815815
		// https://blog.csdn.net/sunlihuo/article/details/80367050

		// https://blog.csdn.net/lovelongjun/article/details/76104192
		// https://blog.csdn.net/xujiangdong1992/article/details/78068903
		// https://www.cnblogs.com/simpledev/p/3842339.html
		// https://my.oschina.net/huangguangsheng/blog/1859404
		// https://blog.csdn.net/u013533380/article/details/78394076
		// https://blog.csdn.net/toxic_guantou/article/details/52605920 可见本初

		// 本类修改代码==》 https://blog.csdn.net/youyou_yo/article/details/51980351

		// 导出压缩文件的全路径
		String zipFilePath = fileOutPath + "data" + startTime + ".zip";
		// 导出zip
		File zip = new File(zipFilePath);
		// 将excel文件生成压缩文件
		File srcfile[] = new File[fileNames.size()];
		for (int j = 0, n1 = fileNames.size(); j < n1; j++) {
			srcfile[j] = new File(fileNames.get(j));
		}
		ZipFiles(srcfile, zip);

		response.setContentType("application/zip");
		response.setHeader("Location", zip.getName());
		response.setHeader("Content-Disposition", "attachment; filename=" + zip.getName());
		OutputStream outputStream = response.getOutputStream();
		InputStream inputStream = new FileInputStream(zipFilePath);
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();

		try {
			delAllFile(fileOutPath); // 删除完里面所有内容
			java.io.File myFilePath = new java.io.File(fileOutPath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	 //压缩文件    
    public void ZipFiles(File[] srcfile, File zipfile) {    
        byte[] buf = new byte[1024];    
        try {    
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(    
                    zipfile));    
            for (int i = 0; i < srcfile.length; i++) {    
                FileInputStream in = new FileInputStream(srcfile[i]);    
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));    
                int len;    
                while ((len = in.read(buf)) > 0) {    
                    out.write(buf, 0, len);    
                }    
                out.closeEntry();    
                in.close();    
            }    
            out.close();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
    }  
	
	 /*** 
     * 删除指定文件夹下所有文件 
     *  
     * @param path 文件夹完整绝对路径 
     * @return 
     */  
    public static  boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                flag = true;  
            }  
        }  
        return flag;  
    }  

}
