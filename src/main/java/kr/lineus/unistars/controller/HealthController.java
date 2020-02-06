package kr.lineus.unistars.controller;


import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.lineus.unistars.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class HealthController {

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    private static String applicationInstanceName = AppController.applicationInstanceName;
    
    private static boolean ok = true;
    private static String dataBaseURL;
    
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    
    @Autowired
    public HealthController(DataSource dataSource) {
        try {
            dataBaseURL = dataSource.getConnection().getMetaData().getURL();
        } catch (Exception e) {
            log.warn("Unable to get configured URL to database", e);
        }
    }
   

    public static String getRunningSince() {
        long uptimeInMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        return Instant.now().minus(uptimeInMillis, ChronoUnit.MILLIS).toString();
    }

    @GetMapping(value = "/health", produces = "application/json; charset=UTF-8")
    public String getHealth(HttpServletResponse response) {
        return getHealthTextJson();
    }

    public String getHealthTextJson() {
        return "{\n" +
                "  \"Status\": \"" + ok + "\",\n" +
                "  \"Version\": \"" + getVersion() + "\",\n" +
                "  \"Database Backend\": \"" + dataBaseURL + "\",\n" +
                "  \"DatabaseSizes\": \"" + getDatabaseSize() + "\",\n" +
                "  \"now\": \"" + Instant.now() + "\",\n" +
                "  \"running since\": \"" + getRunningSince() + "\"\n\n" +
                "}\n";
    }



    private String getDatabaseSize() {
        String indexSize = "user count: " + userService.count();
        return indexSize;
    }


    public String convert(InputStream inputStream, Charset charset) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
    
    public String getVersion() {
        Properties mavenProperties = new Properties();


        try {
            Resource resource = resourceLoader.getResource(CLASSPATH_URL_PREFIX + "/META-INF/maven/kr.lineus/unistars/pom.properties");
            URL mavenVersionResource = resource.getURL();
            mavenProperties.load(mavenVersionResource.openStream());
            return mavenProperties.getProperty("version", "missing version info in " + resource.getFilename()) + " [" + applicationInstanceName + " - " + getMyIPAddresssesString() + "]";
        } catch (IOException e) {
            log.warn("Problem reading version resource from classpath: ", e.getMessage());
        }
        return "(DEV VERSION)" + " [" + applicationInstanceName + " - " + getMyIPAddresssesString() + "]";
    }

    public String getMyIPAddresssesString() {
        String ipAdresses = "";

        try {
            ipAdresses = InetAddress.getLocalHost().getHostAddress();
            Enumeration n = NetworkInterface.getNetworkInterfaces();

            while (n.hasMoreElements()) {
                NetworkInterface e = (NetworkInterface) n.nextElement();

                InetAddress addr;
                for (Enumeration a = e.getInetAddresses(); a.hasMoreElements(); ipAdresses = ipAdresses + "  " + addr.getHostAddress()) {
                    addr = (InetAddress) a.nextElement();
                }
            }
        } catch (Exception var5) {
            ipAdresses = "Not resolved";
        }

        return ipAdresses;
    }
}