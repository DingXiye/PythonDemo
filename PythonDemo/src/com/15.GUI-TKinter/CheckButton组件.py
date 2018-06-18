'''
Created on 2018年6月15日
Checkbutton组件
@author: dingye
'''
from tkinter import *

root =Tk()
#需要一个Tkinter变量，用于表示该按钮是否被选中
v=IntVar()
c=Checkbutton(root,text="测试",variable=v)
c.pack(anchor=W)#左对齐（按照地理位置分）
#如果被选中，v被赋值1，否则0
#再使用label动态展示
l=Label(root,textvariable=v)
l.pack()
mainloop()
