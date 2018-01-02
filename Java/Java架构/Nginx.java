yum -y install wget httpd-tools vim
yum -y install gcc gcc-c++ autoconf pcre pcre-devel make automake
cd /opt; mkdir app download log work backup
启动Nginx:systemctl start nginx.service
检查nginx配置文件: nginx -t -c /etc/nginx/nginx.conf

1.什么是 Nginx:一个开源且高性能,可靠的HTTP中间件、代理服务;
	1.1.为什么选择Nginx:
		(1).Io多路复用epoll---(多路复用:"http://www.cnblogs.com/fanzhidongyzby/p/4098546.html")
		(2).轻量级:功能模块少,代码模块化;
		(3).CPU亲和(affinity):是一种把CPU核心和Nginx工作进程绑定方式,把每个worker进程固定在一个cpu上执行,
			减少切换CPU的cache miss,活动更好的性能;
		(4).sendfile:

2.Nginx安装:
	2.1.Linux安装:(基于yum安装)
		(1).在 /etc/yum.repos.d/ 目录下新建文件 nginx.repo,在文件中输入:
			[nginx]
			name=nginx repo
			baseurl=http://nginx.org/packages/OS/OSRELEASE/$basearch/
			gpgcheck=0
			enabled=1
			==> OS - "rhel" or "centos"
			==> OSRELEASE - 对应的版本,如6,7
		(2).查看nginx相关安装包:
			yum list | grep nginx
		(3).安装:
			yum install nginx
		(4).nginx -v:查看版本好
			nginx -V:查看对应的编译参数
	2.2.
3.Nginx 参数使用:
	3.1.Nginx 安装目录:查看其安装目录 rpm -ql nginx
		(1)./etc/logrotate.d/nginx - nginx 日志轮转,用于 logrotate 服务的日志切割;
		(2)./etc/nginx
			/etc/nginx/nginx.conf - 主配置文件
			/etc/nginx/conf.d
			/etc/nginx/conf.d/default.conf - 默认配置文件
		(3)./etc/nginx/fastcgi_params
			/etc/nginx/scgi_params
			/etc/nginx/uwsgi_params
			cgi配置文件
		(4)./etc/nginx/koi-utf
			/etc/nginx/koi-win
			/etc/nginx/win-utf
			编码转换映射文件
		(5)./etc/nginx/mime.types-设置http协议的content-type与扩展名对应关系;
		(6)./usr/lib/systemd/system/nginx-debug.service
			/usr/lib/systemd/system/nginx.service
			/etc/sysconfig/nginx
			/etc/sysconfig/nginx-debug
			用于配置出系统守护进程管理器管理方式
		(7)./usr/lib64/nginx/modules
			/etc/nginx/modules
			模块目录
			......
		
	3.2.Nginx 编译参数:nginx -V
	3.3.nginx.conf 配置文件:
		(1).user -- 设置nginx服务的系统使用用户
			worker_processes -- 工作进程数(跟CPU个数);
			error_log  -- Nginx的错误日志
			pid   -- Nginx 启动时候pid
			events -- 	work_connections -- 每个进程允许最大连接数(可以优化的参数)
						use --  工作进程数
		(2).http:
4.Nginx 模块:
	4.1.sub_status:(详细参考:http://nginx.org/en/docs/http/ngx_http_stub_status_module.html)
		(1).模块名称:ngx_http_stub_status_module
		(2).用途:显示nginx客户端基本状态信息;
		(3).安装模块:--with-http_stub_status_module
		(4).配置语法:
			Syntax:	stub_status;
			Default:—
			Context:server, location
		(5).示例:
			location /basic_status {
			    stub_status;
			}
			页面访问: 127.0.0.1/mystatus,浏览器显示如下内容
			Active connections: 1 
			server accepts handled requests
			 2 2 2 
			Reading: 0 Writing: 1 Waiting: 0 
	4.2.random_index:
		(1).模块名称:ngx_http_random_index_module
		(2).用途:在对应目录中随机选择一个主页,默认是关闭的,需要安装
		(3).安装模块:--with-http_random_index_module
		(4).配置语法:
			Syntax:	random_index on | off;
			Default:	
			random_index off;
			Context:	location
		(5).示例:
			location / {
			    random_index on;
			}
	4.3.sub_module:
		(1).模块名称:ngx_http_sub_module
		(2).用途:http内容替换
		(3).安装模块:--with-http_sub_module
		(4).配置语法:
			A.sub_filter:替换内容
				Syntax:	sub_filter string replacement;
				Default:	—
				Context:	http, server, location
			B.sub_filter_last_modified:
				Syntax:	sub_filter_last_modified on | off;
				Default: sub_filter_last_modified off;
				Context:http, server, location
			C.sub_filter_once:是否全部替换
				Syntax:	sub_filter_once on | off;
				Default:	
				sub_filter_once on;
				Context:	http, server, location
			D.sub_filter_types:过滤文件类型
				Syntax:	sub_filter_types mime-type ...;
				Default:	
				sub_filter_types text/html;
				Context:	http, server, location
		(5).配置示例:
			location / {
			    sub_filter '<a href="http://127.0.0.1:8080/'  '<a href="https://$host/';
			    sub_filter '<img src="http://127.0.0.1:8080/' '<img src="https://$host/';
			    sub_filter_once on;
			}
	4.4.请求限制
		4.4.1.连接频率限制:
			(1).模块名称:ngx_http_limit_conn_module
			(2).用途:限制并发连接次数
			(3).安装模块:
			(4).配置语法:
				A.limit_conn_zone:key表示限制的条件,比如ip,为其开辟size空间
					Syntax:	limit_conn_zone key zone=name:size;
					Default:	—
					Context:	http
				B.limit_conn: 这里zone是上述limit_conn_zone中name值,number表示并发的限制
					Syntax:	limit_conn zone number;
					Default:	—
					Context:	http, server, location
			(5).配置示例:
				limit_conn_zone $binary_remote_addr zone=perip:10m;
				limit_conn_zone $server_name zone=perserver:10m;
				server {
				    ...
				    limit_conn perip 10;
				    limit_conn perserver 100;
				}
		4.4.2.请求限制:
			(1).模块名称:ngx_http_limit_req_module
			(2).用途:限制客户端请求次数,或者速度等
			(3).安装模块:
			(4).配置语法:
				A.limit_req_zone:
					Syntax:	limit_req_zone key zone=name:size rate=rate;
					Default:	—
					Context:	http
				B.limit_req: 
					Syntax:	limit_req zone=name [burst=number] [nodelay];
					Default:	—
					Context:	http, server, location
			(5).配置示例:
				limit_req_zone $binary_remote_addr zone=perip:10m rate=1r/s;
				limit_req_zone $server_name zone=perserver:10m rate=10r/s;
				server {
				    ...
				    limit_req zone=perip burst=5 nodelay;
				    limit_req zone=perserver burst=10;
				}
		4.4.3.整个配置示例:修改 /nginx/conf.d/default.conf 配置文件
			limit_conn_zone $binary_remote_addr zone=conn_zone:1m;
			limit_req_zone $binary_remote_addr zone=req_zone:1m rate=1r/s;
			server {
			    listen       80;
			    server_name  localhost;

			    #charset koi8-r;
			    #access_log  /var/log/nginx/host.access.log  main;

			    location / {
			        root   /usr/share/nginx/html;
			        #limit_conn conn_zone 1;
			        #limit_req zone=req_zone burst=3 nodelay;
			        #limit_req zone=req_zone burst=3;
			        #limit_req zon e=req_zone;
			        index  index.html index.htm;
			    }
			    ...
			}
	4.5

5.

			























