PPRO projekt – jádro aplikace pro výuku teorie grafů
=====================================================
**Webová aplikace vznikla jako semestrální práce na předmět PPRO. Řeší základní funkčnost jako přidávání obsahu (články a komentáře k nim, obrázky, grafy), správu a přihlašování uživatelů, jednoduchý frontend. Původně měla tvořit základ mnohem větší aplikace, která by vznikala v rámci diplomových prací autorů a byla studentům i učitelům oporou při výuce teorie grafů. K realizaci nakonec nedošlo, protože každý z autorů si pro diplomku vybral jiný projekt.**

Backend
---------
* Spring MVC, Maven, Hibernate ORM, MySQL databáze (s využitím XAMPP)
* konfigurace pomocí Java anotací (web.xml vůbec nepoužíváme)
* bezpečnost (Spring Security, Spring Security Web)
	* autentizace – přihlašování (řešíme pouze pro autory obsahu webu)
		* hashování hesel pomocí BCrypt
		* token při přihlášení
	* autorizace – přístupová práva (role uživatelů – máme připravené dvě, ale došli jsme k závěru, že podobně jako např. na wikipedii bude přihlašování bude jen pro tvůrce obsahu, takže mezi rolemi v aplikaci zatím nerozlišujeme)
	* validace formulářů – v jsp jsou formulářová pole napsána pomocí jsp kódu s využitím css tagů
	* zamezení přístupu (ignorování dotazů) na podsložky Resources
* vyhledávání pomocí Hibernate Search
	* využívá indexování, indexuje při každém uložení / úpravě / odstranění entity pomocí Hibernate
	* vyhledávání v titulcích a textech článků
	* víceslovné výrazy s maximální mezerou (“slop factor”) 4 slova – umožňuje správně najít např. výraz “prohledávání do šířky” i přestože v textu článku je napsáno “prohledávání grafu do šířky”
	* příprava na použití analyzátoru (prvek, který umožňuje osekat předpony a přípony a tím zpracovávat slova v různém tvaru)
* knihovna Lombok pro omezení boilerplate kódu 
	* generuje za nás gettery, settery, metody equals a hashcode, konstruktory. 
* knihovna Imgscalr pro převzorkování vkládaných obrázků
	* zvládá všechny běžné formáty, používáme ji ke generování miniatur
* testy pomocí Spring Boot Test Starter (podporuje práci s JUnit, Hamcrest nebo Mockito)

Frontend
------------
* responzivní šablona na základě knihovny Material Design for Bootstrap
* menu, záhlaví, zápatí a další opakované prvky oddělené jako samostatné soubory
* potvrzovací a chybové hlášky při vyhledávání, registraci a úpravě obsahu
* javascriptová knihovna Cytoscape.js pro zobrazování grafových struktur
* chybové stránky (aspoň některé) – důležité pro udržení uživatele na webu
* bezpečnost – escapování kódu v obsahu článků
	* Zatím není rozhodnuto, co všechno půjde do článků vkládat a jak se budou editovat
* podpora CZ znaků (všude dodrženo kódování UTF8), výběr fontů s podporou všech CZ znaků (zdaleka ne všechny Google Fonts to totiž podporují)
* UI stringy sloučené do jednoho souboru – až na výjimky (provizoria) nemáme žádné hardcoded stringy
	* příprava na lokalizaci UI pro další jazyky
	* snadnější správa a možnost opakovaného použití
	* předpoklad pro zaručení jednotného způsobu komunikace aplikace s uživatelem 
* jednoduché admin rozhraní ala WordPress
* ikony Font Awesome nebo Feather (do budoucna omezíme na pouze jednu knihovnu)

Použité technologie
--------------------
* Spring MVC
* Maven
* Hibernate ORM
* MySQL (Xampp)
* Spring Security, Spring Security Web
* BCrypt hashování hesel
* Spring Boot Test Starter
* Hibernate Search
* Lombok
* Imgscalr
* JSTL (JavaServer Pages Standard Tag Library)
* Cytoscape.js
* Material Design for Bootstrap
* Google Fonts
* Font Awesome / Feather
