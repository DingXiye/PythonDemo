'''
Created on 2018年6月8日
异常处理的知识
@author: dingye
'''
#1.使用try except 
# try:
#     f=open("fa.txt")
# except (OSError,TypeError):
#     print("文件不存在")
# finally:
#     f.close()
    
#2.With
try:
    with open("bu.txt",'w') as f1:
        for e in f1:
            print(e)
except OSError as reason:
    print("出错:"+str(reason))
    