'''
Created on 2018年6月15日
Tkinter
@author: dingye
'''
import tkinter as tk

#创建一个主窗口，用于容纳整个程序
root =tk.Tk()
#设置主窗口的标题栏
root.title("Demo")
#添加一个label组件，可显示文本、图标或图片
theLabel =tk.Label(root,text="我的窗口")
#用于自动调节组件自身尺寸
theLabel.pack()
#显示窗口
root.mainloop()

