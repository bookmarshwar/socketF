import socket
import json,re
host = 'localhost'
port = 9978
class SocketFolk():
    '''
    host:地址\n
    port:端口\n
    '''
    def __init__(self,host,port):
        self.host=host
        self.port=port
    def send(self,data):
        clientTest = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        clientTest.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)  # 在客户端开启心跳维护
        clientTest.connect((host, port))
        clientTest.send((data+'\r\n').encode())
        clientTest.close()
    def get(self,data):
        clientTest = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        clientTest.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)  # 在客户端开启心跳维护
        clientTest.connect((host, port))
        clientTest.send((data+'\r\n').encode())
        info=clientTest.recv(1024)
        clientTest.close()
        return info.decode()
    def command(self,msg):
        '''
        命令{msg}
        '''
        data={ 'mode' : 'command', 'msg' : msg }
        data= json.dumps(data)
        self.send(data)
    def broadcast(self,msg):
        '''
        广播{msg}
        '''
        data={ 'mode' : 'broadcast', 'msg' : msg }
        data= json.dumps(data)
        self.send(data,)
    def get_players(self):
        '''
        返回玩家list
        '''
        data={ 'mode' : 'get_players', 'msg' : '' }
        data=json.dumps(data)
        return re.findall("=(.+?)}",self.get(data))
a=SocketFolk(host='127.0.0.1',port=port)
print(a.get_players())