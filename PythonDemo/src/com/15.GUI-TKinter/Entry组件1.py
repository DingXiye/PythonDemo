'''
Created on 2018年6月17日
Entry组件：平时所说输入框
@author: dingye
'''
from tkinter import *

#布局三种方式
#1.grid():使用表格的形式管理组件位置
#2.place():
#3.pack():
root =Tk()
#root.geometry('300x200')    #设置界面宽和高：是x 不是*
root.resizable(width=False, height=True) #宽不可变, 高可变,默认为True
Label(root,text="账号:").grid(row=0)
Label(root,text="密码:").grid(row=1)
v1=StringVar()
v2=StringVar()

#提供验证合法性功能
def test():
    if e1.get() == "dy":
        print("正确")
        return True 
    else:
        print("错误")
        e1.delete(0,END)
        return False
#validate、validatecommand和invalidcommand三个选项
#foucus：失去焦点时验证
#foucus：失去或获取焦点时验证
#focusin：获取焦点时验证
#all：所有情况下都验证
#key：当输入框编辑时验证
#none：关闭验证功能
e1=Entry(root,textvariable=v1,validate="focusout",validatecommand=test)
e2=Entry(root,textvariable=v2,show="*")
e1.grid(row=0,column=1,padx=10,pady=5)
e2.grid(row=1,column=1,padx=10,pady=5)
def show():
    print("账号：%s"%v1.get())
    print("密码：%s"%v2.get())
    e1.delete(0, END)
    e2.delete(0, END)
Button(root,text="确定",width=10,command=show).grid(row=3,column=0,sticky=W,padx=10,pady=5)    
Button(root,text="取消",width=10,command=root.quit).grid(row=3,column=1,sticky=E,padx=10,pady=5)       
mainloop()