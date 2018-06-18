'''
Created on 2018年6月18日
PandedWindow：空间管理组件，与Frame类似，但这可以调整空间划分
@author: dingye
'''
from tkinter import *

#显示分割线
m=PanedWindow(showhandle=True,sashrelief=SUNKEN)
m.pack(fill=BOTH,expand=1)
left=Label(m,text="left pane")
m.add(left)
m2=PanedWindow(orient=VERTICAL,showhandle=True,sashrelief=SUNKEN)
m.add(m2)

top=Label(m2,text="top pane")
m2.add(top)
bottom=Label(m2,text="bottom pane")
m2.add(bottom)
mainloop()