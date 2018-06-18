'''
Created on 2018年6月17日
Scrollbar
垂直滚动条
1.设置该组件的yscrollbarcommand选项为Scrollbar组件的set方法
2.设置Scrollbar组件的command选项为该组件的yview方法
当用户操作滚动条时，滚动条相应并通过Listbox中yview显示内容，可视范围发生变化时，ListBox组件通过调用set设置滚动条的最新位置
@author: dingye
'''
from tkinter import *
root =Tk()
sb=Scrollbar(root)
sb.pack(side=RIGHT,fill=Y)
lb=Listbox(root,yscrollcommand=sb.set)
for i in range(15):
    lb.insert(END,str(i))
lb.pack(side=LEFT,fill=BOTH)
sb.config(command=lb.yview)
mainloop()