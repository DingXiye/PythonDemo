'''
Created on 2018年6月18日
标准对话框
@author: dingye
'''
import tkinter.filedialog  
import tkinter.colorchooser  
from tkinter import messagebox  
from tkinter import *
root =Tk()
def show():
    #askquestion(title,message,options),askretrycancel(),askyesno(),showerror(),showinfo(),showwarning()
    #options:default,icon,parent
      print(messagebox.askokcancel('messagebox', 'Demo one'))  
Button(root,text="点击",command=show).pack()



#文件对话框
def callback():
    filename=filedialog.askopenfilename()
    print(filename)
Button(root,text="打开文件",command=callback).pack()

#颜色对话框
def color():
    filename=colorchooser.askcolor()
    print(filename)
Button(root,text="选择颜色",command=color).pack()
mainloop()