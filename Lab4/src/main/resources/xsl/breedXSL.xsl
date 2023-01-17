<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Породы</title>
            </head>
            <body>
                <table border="1" style="margin-top: 5px">
                    <tr bgcolor="#CCCCCC">
                        <td>
                            <strong>Id</strong>
                        </td>
                        <td>
                            <strong>Порода</strong>
                        </td>

                    </tr>
                    <xsl:for-each select="ArrayList/item">
                        <tr>
                            <td>
                                <xsl:value-of select="id"/>
                            </td>
                            <td>
                                <xsl:value-of select="breedName"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>