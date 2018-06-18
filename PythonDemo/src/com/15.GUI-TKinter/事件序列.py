'''
Created on 2018年6月18日
事件序列
Tkinter使用一种称为事件序列的机制来允许用户定义事件
<modifier-type-detail>
type：最重要的，他通常用于描述普通事件类型
modifier：用于描述组合件
detail：描述具体案件
@author: dingye
'''
#type中的关几次及含义
#1.Activate：当组件状态由未激活变为激活时触发
#2.Button：用户单机鼠标触发，detail部分制定具体哪个按键。<Button-1>鼠标左键,<Button-2>鼠标中键,<Button-3>鼠标右键,<Button-4>滚轮上滚,<Button-5>滚轮下滚
#3.ButtonRelease：用户释放鼠标时触发
#4.Configure：组件尺寸发生改变时触发
#5.Deactivate：当组件状态由激活变为未激活时触发
#6.Destory：当组件销毁时触发
#7.Enter：鼠标进入组件时触发
#8.Expose：当窗口或组件的某部分不再被覆盖时触发
#9.FocusIn：当组件获得焦点时触发，使用takefocus设为True或者调用focus_set方法
#10.FocusOut：失去焦点时触发
#11.KeyPress：当用户按下键盘某键触发<KeyPress-H>按下H键触发
#12.KeyRelease：释放某键触发
#13.Leave：鼠标离开触发
#14.Map：当组件被映射时触发
#15.Motion：当鼠标在组件内移动的整个过程均触发
#16.MouseWheel：当鼠标滚动时触发
#17.Unmap：当组件被取消映射时触发
#18.Visibility：当应用程序至少有一部分在屏幕中时可见时触发


#modifier常用关键词及含义
#Alt：当按下Alt键时
#Any：表示任何键被按下时
#Control：按下Control时
#Double：当后续两个事件被连续触发时<Double-Button-1>:表示双击左键触发
#Triple：当后续三个被连续触发时
#Lock:当打开大写字母键时
#Shift：当按下Shift键时

#Event对象
#widget：产生该事件的组件
#x，y：鼠标位置（基于窗口左上角）
#x_root,y_root:基于屏幕左上角
#char：按键对应字符
#keysym：按键名
#keycode:按键码
#num：按钮数字
#width,height:组件新尺寸
#type：该事件类型
