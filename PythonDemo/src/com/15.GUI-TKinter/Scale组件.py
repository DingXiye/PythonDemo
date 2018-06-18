'''
Created on 2018年6月17日
Scale组件
@author: dingye
'''
from tkinter import *
root =Tk()
#tickinterval:设置刻度，resolution：设置每点一下走的刻度，tickinterval：设置刻度间隔
s1=Scale(root,from_=0,to=42,tickinterval=10,length=200,resolution=5,orient=VERTICAL)
s1.pack()
s2=Scale(root,from_=0,to=200,orient=HORIZONTAL)
s2.pack()
def show():
    print(s1.get(),s2.get())
Button(root,text="获取位置",command=show).pack()
mainloop()
