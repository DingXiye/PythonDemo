'''
Created on 2018年6月15日
Tkinter
@author: dingye
'''
import tkinter as tk

class App:
    def __init__(self,root):
        #创建一个框架，然后在里边添加一个按钮
        frame=tk.Frame(root)
        #设置位置，padx和pady设置偏移位置
        frame.pack(side=tk.LEFT,padx=10,pady=10)
        #创建一个按钮，fg是设置前景色
        self.hi_there=tk.Button(frame,text="打招呼",fg="blue",command=self.say_hi)
        self.hi_there.pack()
    def say_hi(self):
       print("大家好")
       
#创建一个toplevel的根窗口，并把他作为参数实例app对象
root =tk.Tk()
root.title("问好")
app=App(root)
#开始主事件循环
root.mainloop()     
        
