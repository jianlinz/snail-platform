package indi.jl.sp.log;

import indi.jl.sp.log.enums.FileFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sp.log")
public class SpLogProperties {

    /**
     * 日志文件路径
     */
    private String filePath = "/opt/logs";

    /**
     * 日志输出格式
     */
    private FileFormat fileFormat = FileFormat.TEXT;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private Email email = new Email();

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }

    public static class Email {

        /**
         * 环境描述
         */
        private String envDesc = "SNAIL_PLATFORM";

        /**
         * 是否开启异常日志发送邮件功能 默认关闭
         */
        private Boolean enable = false;

        /**
         * SMTP server的地址，必需指定。如网易的SMTP服务器地址是： smtp.163.com
         */
        private String smtpHost = "smtp.ym.163.com";

        /**
         * SMTP server的端口地址
         */
        private Integer smtpPort = 25;

        /**
         * 发件人账号
         */
        private String username = "system@hopestarting.com";

        /**
         * 发件人密码
         */
        private String password = "system147258";

        /**
         * 如果设置为true，appender将会使用SSL连接到日志服务器
         */
        private Boolean ssl = true;

        /**
         * 指定emial的标题，它需要满足PatternLayout中的格式要求。如果设置成“Log: %logger - %msg ”，就案例来讲，则发送邮件时，标题为“【Error】: com.foo.Bar - Hello World ”。 默认值："%logger{20} - %m".
         */
        private String subject = "【Error】: %logger";

        /**
         * 指定发件人名称。如果设置成“&lt;ADMIN&gt; ”，则邮件发件人将会是“<ADMIN> ”
         */
        private String from = "system@hopestarting.com";

        /**
         * 收件人账号多个可以逗号隔开
         */
        private String to = "jianlin1215@outlook.com";


        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getSmtpHost() {
            return smtpHost;
        }

        public void setSmtpHost(String smtpHost) {
            this.smtpHost = smtpHost;
        }

        public Integer getSmtpPort() {
            return smtpPort;
        }

        public void setSmtpPort(Integer smtpPort) {
            this.smtpPort = smtpPort;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Boolean getSsl() {
            return ssl;
        }

        public void setSsl(Boolean ssl) {
            this.ssl = ssl;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getEnvDesc() {
            return envDesc;
        }

        public void setEnvDesc(String envDesc) {
            this.envDesc = envDesc;
        }
    }
}
