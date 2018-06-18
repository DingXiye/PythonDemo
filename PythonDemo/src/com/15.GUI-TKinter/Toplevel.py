'''
Created on 2018年6月18日
toplevel:独立顶级窗口，拥有标题栏、边框等组件
@author: dingye
'''
from tkinter import *
root =Tk()

def create():
    top=Toplevel()
    top.title("Welcome")
    top.attributes("-alpha",0.5)
    msg=Message(top,text="Hello World")
    msg.pack()

Button(root, text="创建窗口",command=create).pack()
mainloop()