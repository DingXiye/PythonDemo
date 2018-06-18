'''
Created on 2018年6月17日
OptionMenu:下拉组件
@author: dingye
'''
from tkinter import *
root =Tk()
options=[
    "one",
    "two",
    "three"
    ]
variable=StringVar()
variable.set(options[0])
w=OptionMenu(root,variable,*options)
w.pack()
def gets():
    print(variable.get())
Button(root,text="点我",command=gets).pack()
mainloop()
