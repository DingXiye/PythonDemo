'''
Created on 2018年6月15日
Radiobutton组件
@author: dingye
'''
from tkinter import *

root =Tk()
#使用循环处理
Langs=[("java",1),("python",2),("C#",3)]

#需要一个Tkinter变量，用于表示该按钮是否被选中
v=IntVar()
v.set(1)
for lang,num in Langs:
    b=Radiobutton(root,text=lang,variable=v,value=num).pack(anchor=W)

#使用indicatoron去掉小圆圈
Radiobutton(root,text="three",variable=v,value=4,indicatoron=False).pack(fill=X)
mainloop()