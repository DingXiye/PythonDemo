'''
Created on 2018年6月15日
Label组件
@author: dingye
'''
from tkinter import *

#左右显示
def heng():
    root =Tk()
    textL=Label(root,text="oho")
    textL.pack(side=LEFT)
    
    #用PhotoImage实例化一个图片对象(只支持gif)
    photo=PhotoImage(file="1.gif")
    imagelabel=Label(root,image=photo)
    imagelabel.pack(side=RIGHT)
    mainloop()

#背景图片
def bg():
    root =Tk()
    photo =PhotoImage(file="1.gif")
    tel=Label(root,text="学习\n是我快乐",
              image=photo,
              #justify=LEFT,#字符串进行左对齐
              compound=CENTER,#设置文本和图像的混合模式
              font=("华康少女字体",20),#设置字体和字号
              fg="black"#设置文本字体颜色
              )
    tel.pack()
    mainloop()
    
bg()