'''
Created on 2018年6月17日
Entry组件：制作一个计算器
@author: dingye
'''
from tkinter import *

root =Tk()
frame=Frame(root)
frame.pack(padx=10,pady=10)
v1=StringVar()
v2=StringVar()
v3=StringVar()
#content,reason,name与'%P','%v','%W'对应
def test(content,reason,name):
    if content.isdigit():
        print(content,reason,name)
        return True 
    else:
        return False
    
testCMD=root.register(test)#将验证函数包装起来
#key:被编辑是验证
#%P:为文本框最新内容
#%v：当前valide的值
#%W：该组件的名字
#%V：调用验证函数的原因
#%s：调用验证函数前的文本框内容
#%S:表示文本被插入和删除的内容
Entry(frame,textvariable=v1,validate="key",width=10,validatecommand=(testCMD,'%P','%v','%W')).grid(row=0,column=0)
Label(frame,text="+").grid(row=0,column=1)
Entry(frame,textvariable=v2,validate="key",width=10,validatecommand=(testCMD,'%P','%v','%W')).grid(row=0,column=2)
Label(frame,text="=").grid(row=0,column=3)
Entry(frame,textvariable=v3,validate="key",width=10,validatecommand=(testCMD,'%P','%v','%W')).grid(row=0,column=4)
def calc():
   result=int(v1.get())+int(v2.get())
   v3.set(result)
Button(frame,text="计算",command=calc).grid(row=1,column=2,pady=5)        
mainloop()