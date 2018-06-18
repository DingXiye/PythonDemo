'''
Created on 2018年6月17日
Canvas
@author: dingye
'''
from tkinter import *
root =Tk()
w=Canvas(root,width=200,height=100)
w.pack()
#画条黄色的横线
l1=w.create_line(0,50,200,50,fill="yellow")
#画条红色的竖线
l2=w.create_line(100,0,100,100,fill="red",dash=(4,4))
#中间画一个蓝色矩形
r1=w.create_rectangle(50,25,150,75,fill="blue")

#画一个椭圆
w.create_oval(40,20,160,80,fill="pink")

w.create_text(100,50,text="love")
#对图像进行修改
w.coords(l1,0,25,200,25)
w.itemconfig(r1, fill="red")
w.delete(l2)
Button(root,text="删除全部",command=(lambda x=ALL:w.delete(x))).pack()

#画一个个椭圆组成图像
def paint(event):
    x1,y1=(event.x-1),(event.y-1)
    x2,y2=(event.x+1),(event.y+1)
    w.create_oval(x1,y1,x2,y2,fill="black")
w.bind("<B1-Motion>",paint)
Label(root,text="拖动左键画图").pack(side=BOTTOM)
mainloop()
