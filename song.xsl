<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<xsl:text>&#xa;</xsl:text>
		<xsl:for-each select="lfm/album/tracks/track">
			<xsl:value-of select="name"/>;<xsl:value-of select="duration"/>
			<xsl:text>&#xa;</xsl:text>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>