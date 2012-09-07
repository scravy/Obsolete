package net.abusingjava.swing;

import net.abusingjava.Author;
import net.abusingjava.Version;

/**
 * Liste von (offiziellen) Sprachen, d.h. Sprachen die irgendwo Amtssprache sind.
 * <p>
 * Die Kürzel sind ISO-639-3 konform; die Liste der Amtssprachen entstammt der
 * <a href="http://de.wikipedia.org/w/index.php?title=Liste_der_Amtssprachen&oldid=91561057">Wikipedia</a>.
 */
@Author("Julian Fleischer")
@Version("2011-08-01")
public enum Sprache {

	// A
	ABQ("Abasinisch"),
	ADY("Adygeisch"),
	AFR("Afrikaans"),
	SQI("Albanisch"),
		ALN("Gegisch", SQI),
		AAT("Arvanitika", SQI),
		ALS("Toskisch", SQI),
		AAE("Arbëresh", SQI),
	AMH("Amharisch"),
	ARA("Arabisch"),
	OCI("Aranesisch / Okzitanisch / Gascognisch"),
	HYE("Aramenisch"),
	AZE("Aserbaidschanisch / Aseri"),
		AZJ("Nordaserbaidschanisch", AZE),
		AZB("Südaserbaidschanisch", AZE),
	ASM("Assamesisch"),
	AVA("Awarisch"),
	AYM("Aymara"),
		AYC("Süd-Aymara"),
		AYR("Zentral-Aymara"),
	
	// B
	KRC("Balkarisch"),
	EUS("Baskisch"),
	BAK("Baschkirisch"),
	BEN("Bengalisch"),
	MYA("Birmanisch"),
	BIS("Bislama"),
	BRX("Bodo"),
	BUL("Bulgarisch"),
	SLA("Burgenlandkoratisch"), // Kein ISO 639-3 Code ??
	BUR("Burjatisch"),
		BXU("Burjatisch (chinesisch)", BUR),
		BXM("Burjatisch (mongolisch)", BUR),
		BXR("Burjatisch (russisch)", BUR),
		
	// C
	KJH("Chakassisch"),
	NYA("Chichewa"),
	ZHO("Chinesisch"),
		CMN("Mandarin-Chinesisch", ZHO),
		GAN("Gan-Chinesisch", ZHO),
		HAK("Hakka-Chinesisch", ZHO),
		CZH("Huizhou-Chinesisch", ZHO),
		CJY("Jinyu-Chinesisch", ZHO),
		LZH("Schrift-Chinesisch", ZHO),
		MNP("Min-Bei-Chinesisch", ZHO),
		CDO("Min-Dong-Chinesisch", ZHO),
		NAN("Min-Nan-Chinesisch", ZHO),
		CZO("Min-Zhong-Chinesisch", ZHO),
		CPX("Pu-Xian-Chinesisch", ZHO),
		WUU("Wu-Chinesisch", ZHO),
		HSN("Xiang-Chinesisch", ZHO),
		YUE("Yue-Chinesisch", ZHO),
	RAR("Cook Island Maori / Raratongaisch"),
	
	// D
	DAN("Dänisch"),
	DEU("Deutsch"),
	DIV("Dhivehi"),
	DOI("Dogri / Kangri"),
		DGO("Dogri", DOI),
		XNR("Kangri", DOI),
	DZO("Dzongkha"),
	
	// E
	BIN("Edo / Bini"),
	END("Englisch"),
	EST("Estnisch"),
	
	// F
	FAO("Färöisch"),
	FIJ("Fidschi"),
	FIN("Finnisch"),
	FRA("Französisch"),
	FRY("West-Frisisch"),
	FRR("Nord-Frisisch"),
	STQ("Saterfriesisch"),
	
	// G
	GAG("Gagausisch"),
	GIG("Galicisch"),
	KAT("Georgisch"),
	ELL("Griechisch"),
	GRN("Guaraní"),
	GUJ("Gujarati"),
	
	// H
	HAT("Haitianische"),
	HAW("Hawaiisch"),
	HBO("Hebräisch"),
	HIN("Hindi"),
	
	// I
	IND("Indonesisch"),
	INH("Inguschisch"),
	IKT("Inuinnaqtun"),
		IKU("Inuktitut", IKT),
	GLE("Irisch"),
	ISL("Isländisch"),
	ITA("Italienisch"),
	
	// J
	SAH("Jakutisch"),
	JPN("Japanisch"),
	YID("Jiddisch"),
		YDD("Ost-Jiddisch"),
		YIH("West-Jiddisch"),
		
	// K
	KBD("Kabardinisch"),
	XAL("Kalmückische"),
	KAN("Kannada / Kanaresisch"),
	KAA("Karakalpakische"),
	KRI("Karelisch"),
	KAS("Kashmiri"),
	KAZ("Kasachisch"),
	CAT("Katalanisch"),
	KHM("Khmer"),
	KIN("Kinyarwanda"),
	KIR("Kirgisisch"),
	GIL("Kiribati / Gilbertesisch"),
	RUN("Kirundi"),
	SWA("Kiswahili"),
		SWH("Swahili", SWA),
		SWC("Copperbelt Swahili", SWA),
	KOM("Komi"),
		KPV("Komi-Syrjänisch"),
		KOI("Komi-Permjakisch"),
	KOK("Konkani"),
		GOM("Goa Konkani", KOK),
		KNN("Standard Konkani", KOK),
	KOR("Koreanisch"),
	KUR("Kurdisch"),
	
	// L
	LLD("Ladinisch"),
	LAO("Lao"),
	LAT("Latein"),
	LAV("Lettisch"),
	LIT("Litauisch"),
	LTZ("Luxemburgisch"),
	
	// M
	MLG("Malagasy"),
	MAI("Maithili"),
	MSA("Malaiisch"),
	MAL("Malayalam"),
	MLT("Maltesische"),
	MRI("Maorisch"),
	MAR("Marathi"),
	CHM("Mari"),
		MHR("Ostmari / Wiesenmari", CHM),
		MRJ("Westmari / Bergmari", CHM),
	MKD("Mazedonisch"),
	MNI("Meitei"),
	MON("Mongolisch"),
	KHK("Halh Mongolisch", MON),
	MYV("Mordwinisch (Ersjanisch)"),
	MDF("Mordwinisch (Mokschanisch)"),
	
	// N
	NAU("Nauruisch"),
	NDE("isiNdebele"), // Das IST korrekt geschrieben
	NEP("Nepali"),
	SGN("Neuseeländische Gebärdensprache"),
	NDS("Niederdeutsch"),
	NLD("Niederländisch"),
	NIU("Niueanisch"),
	NSO("Nord-Sotho"),
	NOR("Norwegisch"),
		NOB("Bokmål", NOR),
		NNO("Nynorsk", NOR),
	
	// O
	ORI("Oriya"),
	OSS("Ossetisch"),
	
	// P
	PAN("Panjabi"),
	PUS("Passchtu"),
	FAS("Persisch"),
	FIL("Pilipino"),
	POL("Polnisch"),
	POR("Portugisisch"),
	
	// Q
	QUE("Quechua"),
	
	// R
	ROH("Rätoromanisch / Bündnerromanisch"),
	RON("Rumänisch / Moldauisch"),
	RUS("Russisch"),
	RUE("Russinisch"),
	
	// S
	SAG("Sango"),
	SAN("Sanskrit"),
	SAT("Santali"),
	SRD("Sardisch"),
	GLA("Schottisch-Gälisch"),
	SWE("Schwedisch"),
	TSN("Setswana"),
	HBS("Serbokroatisch"),
		HRV("Kroatisch", HBS),
		BOS("Bosnisch", HBS),
		SRP("Serbisch / Montenegrinisch", HBS),
	SOT("Sesotho"),
	CPF("Seychellenkreol"), // Kein ISO ...-3 ??
	SND("Sindhi"),
	SIN("Singhalesisch"),
	SSW("Siswati"),
	SLK("Slowakisch"),
	SLV("Slowenisch"),
	SOM("Somali"),
	SPA("Spanisch"),
	
	// T
	TGK("Tadschikisch"),
	TAM("Tamil"),
	TAT("Tatarisch"),
	TEL("Telugu"),
	TET("Tetum"),
	THA("Thailändisch"),
	BOD("Tibetisch"), // 3 Sprachen...
	TIR("Tigrinya"),
	TON("Tongaisch"),
	CES("Tschechisch"),
	CHE("Tschetschenisch"),
	TUR("Türkisch"),
	TUK("Turkmenisch"),
	TVI("Tuvaluisch"),
	TYV("Tuwinisch"),
	
	// U
	UDM("Udmurtisch"),
	UIG("Uigrisch"),
	UKR("Ukrainisch / Ruthenisch"),
	HUN("Ungarisch"),
	URD("Urdu"),
	UZB("Usbekisch"),
	
	// V
	VEN("Venda / Tshivenda"),
	VIE("Vietnamesisch"),
	
	// W
	CYM("Walisisch"),
	BEL("Weißrussisch / Belarussisch"),
	WOL("Wolof (Senegal)"),
	WOF("Wolof (Gambia)"),
	
	// X
	XHO("isiXhosa"), // Ja, das IST richtig geschrieben
	TSO("Xitsonga"),
	
	// Z
	ZHA("Zhuang"),
	ZUL("isiZulu"); // Das auch.
	
	
	private final String $name;
	private final Sprache $parent;
	
	Sprache(final String $name) {
		this.$name = $name;
		this.$parent = null;
	}
	
	Sprache(final String $name, final Sprache $parent) {
		this.$name = $name;
		this.$parent = $parent;
	}
	
	@Override
	public String toString() {
		return $name;
	}
	
	public Sprache getParent() {
		return $parent;
	}
}
