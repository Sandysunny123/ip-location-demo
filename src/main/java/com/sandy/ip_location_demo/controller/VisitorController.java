package com.sandy.ip_location_demo.controller;

import com.sandy.ip_location_demo.service.VisitorLocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorController {

    private final VisitorLocationService service;

    public VisitorController(VisitorLocationService service) {
        this.service = service;
    }

    @GetMapping("/visit")
    public String visit() {

        return """
                <html>
                <body>
                    <h2>📍 Location Demo</h2>

                    <p>
                        This demo uses your IP address to estimate
                        your approximate city/state/country.
                    </p>

                    <p>
                        Your location is not precise GPS location.
                    </p>

                    <form method="post" action="/visit/consent">
                        <button type="submit">
                            Continue and share approximate location
                        </button>
                    </form>
                </body>
                </html>
                """;
    }

    @PostMapping("/visit/consent")
    public String consent(HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        System.out.println("Visitor IP: " + ip);
        System.out.println("User Agent: " + request.getHeader("User-Agent"));

        service.saveVisitor(ip);

        var geo = service.saveVisitor(ip);

        if (geo == null) {
            return """
            <html>
            <body>
                <h2>⚠️ Local development</h2>
                <p>
                    Your browser is connected to localhost,
                    so a public IP location cannot be determined.
                </p>
            </body>
            </html>
            """;
        }

        return """
        <html>
        <body>
            <h2>✅ Approximate Location</h2>
            <p>City: %s</p>
            <p>State/Region: %s</p>
            <p>Country: %s</p>
            </body>
        </html>
        """.formatted(
                geo.city(),
                geo.region(),
                geo.country()
        );
    }


}