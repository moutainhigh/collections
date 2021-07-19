import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import login from '@/components/login'
import weiHu from '@/components/safe/weiHu'
import buy from '@/components/safe/buy'
import specialBuy from '@/components/safe/specialBuy'
import tempBuy from '@/components/safe/tempBuy'
import sell from '@/components/safe/sell'

import Shi_Ri_Day_Up from '@/components/safe/Shi_Ri_Day_Up'
import wuRiAndShiRiChongHe from '@/components/safe/WuRiShiRiChongHe'
import weekMa10DaYuMa20 from '@/components/safe/weekMa10DaYuMa20'
import dayMa10DaYuMa20 from '@/components/safe/dayMa10DaYuMa20'
import shouYangOneList from '@/components/safe/shouYangOneList'
import shouYangTwoList from '@/components/safe/shouYangTwoList'
import shouYangThreeList from '@/components/safe/shouYangThreeList'
import tradingTwoDayUp from '@/components/safe/tradingTwoDayUp'
import tradingOneDayUp from '@/components/safe/tradingOneDayUp'
import holderDayUp from '@/components/safe/holderDayUp'
import setting from '@/components/safe/setting'
import email from '@/components/poster/email'
import sms from '@/components/poster/sms'

import dayma5Vol from '@/components/safe/dayma5Vol'

import dayma5VolBigMa10Vol from '@/components/safe/dayma5BigMa10Vol'

import weekma5Vol from '@/components/safe/weekMa5Vol'

import weekMa5VolBigMa10 from '@/components/safe/weekMa5VolBigMa10Vol'
import traddingMa5Vol from '@/components/safe/traddingMa5Vol'
import traddingMa5VolBigMa10Vol from '@/components/safe/traddingMa5VolBigMa10Vol'


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
      path: '/buy',
      name: 'buy',
      component: buy
    },
    {
      path: '/specialBuy',
      name: 'specialBuy',
      component: specialBuy
    },
    {
      path: '/Shi_Ri_Day_Up',
      name: 'Shi_Ri_Day_Up',
      component: Shi_Ri_Day_Up
    },

		 {
		  path: '/tempBuy',
		  name: 'tempBuy',
		  component: tempBuy
		},
    {
      path: '/sell',
      name: 'sell',
      component: sell
    }

		, {
		  path: '/shouYangThreeList',
		  name: 'shouYangThreeList',
		  component: shouYangThreeList
		},
    {
		  path: '/weekMa10DaYuMa20',
		  name: 'weekMa10DaYuMa20',
		  component: weekMa10DaYuMa20
		},
    {
      path: '/dayMa10DaYuMa20',
      name: 'dayMa10DaYuMa20',
      component: dayMa10DaYuMa20
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
		  path: '/sms',
		  name: 'sms',
		  component: sms
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
      path: '/tradingOneDayUp',
      name: 'tradingOneDayUp',
      component: tradingOneDayUp
    },

		{
		  path: '/tradingTwoDayUp',
		  name: 'tradingTwoDayUp',
		  component: tradingTwoDayUp
		},
		{
		  path: '/holderDayUp',
		  name: 'holderDayUp',
		  component: holderDayUp
		},{
		  path: '/wuRiAndShiRiChongHe',
		  name: 'wuRiAndShiRiChongHe',
		  component: wuRiAndShiRiChongHe
		},

{
		  path: '/dayma5Vol',
		  name: 'dayma5Vol',
		  component: dayma5Vol
		},


,{
		  path: '/dayma5VolBigMa10Vol',
		  name: 'dayma5VolBigMa10Vol',
		  component: dayma5VolBigMa10Vol
		},{
		  path: '/weekma5Vol',
		  name: 'weekma5Vol',
		  component: weekma5Vol
		},
  {
		  path: '/weekMa5VolBigMa10',
		  name: 'weekMa5VolBigMa10',
		  component: weekMa5VolBigMa10
		},

    {
    	  path: '/traddingMa5Vol',
    	  name: 'traddingMa5Vol',
    	  component: traddingMa5Vol
    	},

      {
      	  path: '/traddingMa5VolBigMa10Vol',
      	  name: 'traddingMa5VolBigMa10Vol',
      	  component: traddingMa5VolBigMa10Vol
      	},
  ]

})



//https://blog.csdn.net/Fabulous1111/article/details/78848971
