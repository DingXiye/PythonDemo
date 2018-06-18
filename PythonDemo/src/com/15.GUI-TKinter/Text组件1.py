'''
Created on 2018年6月17日
Text组件:用于显示和处理多行文本
Tags:改变文本样式和功能
@author: dingye
'''
import webbrowser
from tkinter import *
root =Tk()
text=Text(root,width=30,height=20)
text.pack()
#INSERT表示插入光标当前位置
text.insert(INSERT, "L love\n")
text.insert(END,"shen")

#输出第一行的第2个位置到第4个位置
print(text.get('1.2', '1.4')) 
#设置一个不可见的标记，在标记处插入文本
text.mark_set("here", "1.2")
text.mark_gravity("here", LEFT)#
text.insert("here","very ")
text.insert("here","...")
text.mark_unset()#删除标记

#Tags
text.tag_add("tag1", "1.5","1.8","1.10")
#设置样式
text.tag_config("tag1", background="yellow",foreground="red")
text.tag_config("tag1", underline=True)

def show_hand_cursor(event):
    text.config(cursor="arrow")
def show_arrow_cursor(event):
    text.config(cursor="xterm")
def click(event):
    webbrowser.open("http://www.baidu.com")
text.tag_bind("tag1", "<Enter>", show_hand_cursor)
text.tag_bind("tag1", "<Leave>", show_arrow_cursor)
text.tag_bind("tag1", "<Button-1>", click)
photo=PhotoImage(file='1.gif')
def showimg():
    text.image_create(END, image=photo)
def show():
    print("dianji")
b=Button(text,text="点我",command=showimg)
text.window_create(INSERT, window=b)
mainloop()