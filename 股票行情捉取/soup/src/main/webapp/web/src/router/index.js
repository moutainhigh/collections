import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import login from '@/components/login'
import weiHu from '@/components/safe/weiHu'

import shouYangOneList from '@/components/safe/shouYangOneList'
import ma20Up from '@/components/safe/ma20Up'
import guanzhu from '@/components/safe/guanzhu'
import dayang from '@/components/safe/daYang'
import closePriceMa5Comp from '@/components/safe/closePriceMa5Comp'
import shouYangTwoList from '@/components/safe/shouYangTwoList'
import closePriceBigMa30 from '@/components/safe/closePriceBigMa30'
import yangDaYuYin from '@/components/safe/yangDaYuYin'
import setting from '@/components/safe/setting'
import email from '@/components/poster/email'


Vue.use(Router)

export default new Router({
  routes: [
		{
		  path: '/',
		  name: 'index',
		  component: index
		},
     {
      path: '/login',
      name: 'login',
      component: login
    },

    {
      path: '/weiHu',
      name: 'weiHu',
      component: weiHu,
      /* meta: { requiresAuth: true }*/
    },

    {
      path: '/closePriceBigMa30',
      name: 'closePriceBigMa30',
      component: closePriceBigMa30
    },
		{
		  path: '/setting',
		  name: 'setting',
		  component: setting
		},
		{
		  path: '/shouYangOneList',
		  name: 'shouYangOneList',
		  component: shouYangOneList
		},
			{
		  path: '/email',
		  name: 'email',
		  component: email
		},
			{
		  path: '/shouYangTwoList',
		  name: 'shouYangTwoList',
		  component: shouYangTwoList
		},
    {
      path: '/dayang',
      name: 'dayang',
      component: dayang
    },
    {
      path: '/closePriceMa5Comp',
      name: 'closePriceMa5Comp',
      component: closePriceMa5Comp
    },{
      path: '/guanzhu',
      name: 'guanzhu',
      component: guanzhu
    },
    {
      path: '/yangDaYuYin',
      name: 'yangDaYuYin',
      component: yangDaYuYin
    },
    {
        path: '/ma20Up',
        name: 'ma20Up',
        component: ma20Up
      },
]


})