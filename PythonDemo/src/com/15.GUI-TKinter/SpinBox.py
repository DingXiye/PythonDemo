'''
Created on 2018年6月17日
SpanBox:从一些固定值中选择一个
@author: dingye
'''
from tkinter import *
root =Tk()
w=Spinbox(root,from_=0,to=10)
w.pack()
Spinbox(root,values=("xiye","shen","xiao")).pack()
mainloop()