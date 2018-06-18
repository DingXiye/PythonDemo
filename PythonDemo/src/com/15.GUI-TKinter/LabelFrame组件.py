'''
Created on 2018年6月17日
LabelFrame组件
@author: dingye
'''
from tkinter import *

root=Tk()
group=LabelFrame(root,text="最好的脚本语言是?",padx=5,pady=5)
group.pack(padx=10,pady=10)
Langs=[("Python",123),("java",234)]
v=IntVar()
v.set(1)#都未选中时输出到label的值
for lang,num in Langs:
    b=Radiobutton(group,text=lang,variable=v,value=num)
    b.pack(anchor=W)
l=Label(root,textvariable=v)
l.pack()
mainloop()