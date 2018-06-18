'''
Created on 2018年6月18日
布局管理器
pack，grid，place
@author: dingye
'''
#1.pack：按添加纵向排列
from tkinter import *
root =Tk()
#fill:该组件填充整个分配给他的空间，both表示同时横向和纵向扩展
#expand：将父组件的额外空间也填满
Label(root,text="label1").pack(fill=BOTH,expand=True)
Label(root,text="label2").pack()

#2.grid：按行列排列，默认情况下居中显示在对应的网格中，使用sticky修改这一特性，EWSN分别表示东西南北

#3.place：允许程序员指定组件大小及位置
def callback():
    print("正中靶心")
photo=PhotoImage(file="1.gif")
Label(root,image=photo).pack()
#relx相对于父组件位置，范围0~1
Button(root,text="点我",command=callback).place(relx=0.5,rely=0.5,anchor=CENTER)

mainloop()
