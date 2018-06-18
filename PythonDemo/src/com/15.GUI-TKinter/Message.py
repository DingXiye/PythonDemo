'''
Created on 2018年6月17日
Message消息组件
@author: dingye
'''
from tkinter import *
root =Tk()

w=Message(root,text="这是一则消息",width=100)
w.pack()
w2=Message(root,text="这也是一则消息",width=100)
w2.pack()
mainloop()