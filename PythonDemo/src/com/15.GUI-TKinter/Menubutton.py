'''
Created on 2018年6月17日
MenuButton：它可放在任意位置
@author: dingye
'''
from tkinter import *
root =Tk()
def callback():
    print("调用")

mb=Menubutton(root,text="点我",relief=RAISED)
mb.pack()
filemenu=Menu(mb,tearoff=False)
filemenu.add_command(label="打开",command=callback)
filemenu.add_checkbutton(label="保存",command=callback)
filemenu.add_separator()#分割符
filemenu.add_command(label="退出",command=root.quit)
mb.config(menu=filemenu)
mainloop()