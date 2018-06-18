'''
Created on 2018年6月17日
ListBox通过列表形式展示出来
@author: dingye
'''
from tkinter import *

root =Tk()
#height设置显示高度
theLB=Listbox(root,setgrid=True,height=15)
theLB.pack()
for item in range(15):
    theLB.insert(END,item)
#删除ACTIVW状态下的项目
bt=Button(root,text="删除",command=lambda x=theLB:x.delete(ACTIVE))
bt.pack()
mainloop()