'''
Created on 2018年6月18日
事件绑定
@author: dingye
'''
from tkinter import *
root =Tk()
def callback(event):
    print("点击位置是：",event.x,event.y)
    print("敲击位置：",repr(event.char))

frame=Frame(root,width=200,height=200)
#鼠标点击位置
frame.bind("<Button-1>",callback)
#键盘敲击位置
frame.bind("<Key>",callback)
frame.focus_set()
#获取鼠标移动轨迹
frame.bind("<Motion>",callback)
frame.pack()
mainloop()