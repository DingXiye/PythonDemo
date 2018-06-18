'''
Created on 2018年6月17日
Menu
@author: dingye
'''
from tkinter import *
root =Tk()
def callback():
    print("diaoyong ")
saveVar=IntVar() 
editVar=IntVar()  
#创建一个顶级菜单
menubar=Menu(root)
#创建一个文件菜单
filemenu=Menu(menubar,tearoff=False)
filemenu.add_command(label="打开",command=callback)
filemenu.add_checkbutton(label="保存",command=callback,variable=saveVar)
filemenu.add_separator()#分割符
filemenu.add_command(label="退出",command=root.quit)
menubar.add_cascade(label="文件",menu=filemenu)
#创建一个编辑菜单
editmenu=Menu(menubar,tearoff=False)
editmenu.add_radiobutton(label="剪切",command=callback,variable=editVar,value=1)
editmenu.add_radiobutton(label="拷贝",command=callback,variable=editVar,value=2)
editmenu.add_radiobutton(label="粘贴",command=callback,variable=editVar,value=3)
menubar.add_cascade(label="编辑",menu=editmenu)
#显示菜单
root.config(menu=menubar)


#实现右击弹出菜单
rightmenu=Menu(root,tearoff=False)
rightmenu.add_command(label="刷新",command=callback)
frame=Frame(root,width=512,height=512)
frame.pack()
def pop(event):
    rightmenu.post(event.x_root,event.y_root)
frame.bind("<Button-3>",pop)#绑定到鼠标右键

mainloop()