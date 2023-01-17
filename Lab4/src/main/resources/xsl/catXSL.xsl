<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Животные</title>
            </head>
            <body>

                <table border="1" style="margin-top: 5px">
                    <tr bgcolor="#CCCCCC">
                        <td>
                            <strong>Имя</strong>
                        </td>
                        <td>
                            <strong>Цвет</strong>
                        </td>
                        <td>
                            <strong>Возраст</strong>
                        </td>
                        <td>
                            <strong>Порода</strong>
                        </td>
                    </tr>
                    <xsl:for-each select="ArrayList/item">
                        <tr>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="color"/>
                            </td>
                            <td>
                                <xsl:value-of select="age"/>
                            </td>
                            <td>
                                <xsl:value-of select="breed/breedName"/>

                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>