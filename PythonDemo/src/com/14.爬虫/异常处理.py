'''
Created on 2018年6月15日
异常处理
@author: dingye
'''
#第一种方法 
from urllib.request import Request,urlopen
from urllib.error import URLError,HTTPError

req=Request('http://www.baidu.com/')
try:
    reponse=urlopen(req)
except HTTPError as h:
    print("The server count\'t fulfill the request.")
    print("Error code:",h.code)
except URLError as e :
    print("We falied to reach a server.")
