'''
Created on 2018年6月13日

@author: dingye
'''
import urllib.request 
#读取图片
req=urllib.request.Request("http://placekitten.com/g/200/300")
reponse=urllib.request.urlopen(req)
print("请求的url:",reponse.geturl())
print("httplib.HTTPMessage对象:",reponse.info())
print("http状态码:",reponse.getcode())
html=reponse.read()
with open('cat_200_300.jpg','wb') as f:#with用法：将代码块执行后返回给as后的变量
    f.write(html)
    
#翻译文本,有错误
import urllib.parse
import json
content=input("请输入翻译内容:")
url="http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=null"
data={}
data['type']='AUTO'
data['i']=content
data['doctype']='json'
data['xmlVersion']='2.1'
data['keyfrom']='fanyi.web'
data['ue']='UTF-8'
data['typoResult']='true'
data=urllib.parse.urlencode(data).encode('utf-8')
response=urllib.request.urlopen(url,data)
html=response.read().decode('utf-8')
target=json.loads(html)
print("翻译结果：%s"%(target['translateResult'][0][0]['tgt']))
