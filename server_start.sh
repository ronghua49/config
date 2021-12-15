#!/bin/bash
echo "重启服务"
jarname=$1
jarurl=${jarname%.*}
echo "$jarurl"
####获取进程id
pid=`ps aux | grep -w $jarname | grep -v grep |grep -v "$0" | awk '{print $2}'`
echo $pid
#####杀死存在进程
if [ -z $pid ];then
        /usr/java/jdk1.8.0_131/bin/java -jar -Xmx1048m -Xms512m -XX:-UseGCOverheadLimit  /home/shuyueinventory/$jarname --spring.profiles.active=qs-uat >/dev/null &
else
        kill -9 $pid
        sleep 3
        /usr/java/jdk1.8.0_131/bin/java -jar -Xmx1048m -Xms512m -XX:-UseGCOverheadLimit  /home/shuyueinventory/$jarname --spring.profiles.active=qs-uat >/dev/null &
fi
####启动###
if [ -n "`ps -ef|grep -v grep |grep -w "$jarname"`" ];then
        echo "启动成功"
else
        echo "启动失败"
fi
