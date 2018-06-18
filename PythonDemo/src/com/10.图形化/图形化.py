'''
Created on 2018年6月8日
图形化界面入门
@author: Administrator
'''
import easygui as g
import sys

while 1:
        g.msgbox("欢迎进入")
        msg="知识选项"
        title="互动"
        choices=["游戏","恋爱","编程"]
        choice=g.choicebox(msg, title, choices)
        g.msgbox("你的选择是："+str(choice),"结果")
        msg="重新选择"
        title="请选择"
        if g.ccbox(msg, title):#展示选择框
            pass
        else:
            sys.exit(0)
        
        