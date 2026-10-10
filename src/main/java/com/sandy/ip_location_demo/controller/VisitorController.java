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
                <!DOCTYPE html>
                <html>
                <head>
                    <meta property="og:title" content="Click Me 🐵">
                    <meta property="og:description" content="Something fun for you 😄">
                    <meta property="og:image" content="https://ip-location-demo-xz1z.onrender.com/click-me.png">
                    <meta property="og:image:type" content="image/png">
                    <meta property="og:image:width" content="1295">
                    <meta property="og:image:height" content="1214">
                    <meta property="og:url" content="https://ip-location-demo-xz1z.onrender.com/visit">
                    <meta property="og:type" content="website">
                    <style>
                        body {
                            margin: 0;
                            background: #f5f5f5;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                        }

                        .container {
                            text-align: center;
                        }

                        img {
                            width: 90%;
                            max-width: 700px;
                            border-radius: 20px;
                            box-shadow: 0 5px 25px rgba(0,0,0,0.2);
                        }

                        button {
                            display: block;
                            margin: 20px auto;
                            padding: 15px 50px;
                            font-size: 22px;
                            font-weight: bold;
                            border: none;
                            border-radius: 30px;
                            background: #ffd21c;
                            cursor: pointer;
                        }
                    </style>
                </head>

                <body>

                <div class="container">

                 <img src="/click-me.png" alt="Click Me">
                                  
                                  <p style="margin-top: 20px;">
                                  </p>
                                  
                                  <form action="/consent" method="post">
                                      <button type="submit">
                                          Continue
                                      </button>
                                  </form>

                </div>

                </body>
                </html>
                """;
    }

    @PostMapping("/consent")
    public String consent(HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        service.saveVisitor(ip);

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>HAHAHA 😂</title>
                    <style>
                        body {
                            margin: 0;
                            background: #111;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                        }

                        img {
                            width: 90%;
                            max-width: 700px;
                            border-radius: 20px;
                        }
                    </style>
                </head>

                <body>

                    <img src="/got-you.png" alt="HAHAHA GOT YOU!">

                </body>
                </html>
                """;
    }
}