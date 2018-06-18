'''
Created on 2018年6月17日
Text组件:用于显示和处理多行文本

@author: dingye
'''
import hashlib
from tkinter import *
from _operator import pos
root =Tk()
text=Text(root,width=30,height=20,undo=True,autoseparators=False,maxundo=10)#实现撤销操作
text.pack()
#INSERT表示插入光标当前位置
text.insert(INSERT, "I love Shen\n")
text.insert(INSERT, "love")
contents =text.get(1.0,END)

#1判断内容是否发生变化
def gets(contents):
    m=hashlib.md5(contents.encode())
    return m.digest()
sig=gets(contents)
def check():
    contents=text.get(1.0,END)
    if sig!=gets(contents):
        print("内容发生改变")
    else:
        print("无变化")
Button(root,text="检查",command=check).pack()   

#2查找操作，使用search方法搜索Text中的内容
def getIndex(text,pos):
    print(text.index(pos))
    #将任何格式的索引号统一为元祖格式输出
    return tuple(map(int,str.split(pos, ".")))
start=1.0
while True:
    pos=text.search("o", start, stopindex=END)
    print(pos)
    if not pos:
        break
    print("找到了，位置是:",getIndex(text, pos))
    start=pos+"+1c"#将start指向下一个字符
    
#3恢复及撤销操作,在Text中设置undo为True，autoseparators设为False
# def shows():
#     text.edit_undo()
# Button(root,text="撤销",command=shows).pack()

def callback(event):
    text.edit_separator()
text.bind('<Key>',callback)
def show():
    text.edit_undo()
Button(root,text="撤销一个字符",command=show).pack()    
    
mainloop()