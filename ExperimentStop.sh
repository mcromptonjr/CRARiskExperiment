sshvm root@C0 'screen -X -S control kill'
sshvm root@C1 'screen -X -S control kill'
sshvm root@C2 'screen -X -S control kill'
sshvm root@ISP0 'screen -X -S control kill'
sshvm root@ISP1 'screen -X -S control kill'
sshvm root@BGP0 'screen -X -S control kill'
sshvm root@BGP1 'screen -X -S control kill'
sshvm root@Router0 'screen -X -S control kill'
sshvm root@Router1 'screen -X -S control kill'
sshvm root@Auth 'screen -X -S control kill'
sshvm root@DB 'screen -X -S control kill'
cd commands
rm -rf *
cd ..
rm -rf results