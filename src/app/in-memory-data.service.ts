import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Region } from "./interfaces/Region";
import { Fact } from "./interfaces/Fact";
import { Type } from "../constants";
import { Poi } from "./interfaces/Poi";

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const regions: Region[] = [
      {
        regionId: "BG-01",
        name: "Благоевград",
        population: 310321,
        area: 6449.47,
        imageUrl: "assets/files/img/regions/BG-01.gif"
      },
      {
        regionId: "BG-02",
        name: "Бургас",
        population: 412684,
        area: 7748.1,
        imageUrl: "assets/files/img/regions/BG-02.gif"
      },
      {
        regionId: "BG-03",
        name: "Варна",
        population: 472654,
        area: 3818,
        imageUrl: "assets/files/img/regions/BG-03.gif"
      },
      {
        regionId: "BG-04",
        name: "Велико Търново",
        population: 242259,
        area: 4662,
        imageUrl: "assets/files/img/regions/BG-04.gif"
      },
      {
        regionId: "BG-05",
        name: "Видин",
        population: 88867,
        area: 3033,
        imageUrl: "assets/files/img/regions/BG-05.gif"
      },
      {
        regionId: "BG-06",
        name: "Враца",
        population: 168727,
        area: 3619.7,
        imageUrl: "assets/files/img/regions/BG-06.gif"
      },
      {
        regionId: "BG-07",
        name: "Габрово",
        population: 112334,
        area: 2023,
        imageUrl: "assets/files/img/regions/BG-07.gif"
      },
      {
        regionId: "BG-08",
        name: "Добрич",
        population: 178438,
        area: 4719.7,
        imageUrl: "assets/files/img/regions/BG-08.gif"
      },
      {
        regionId: "BG-09",
        name: "Кърджали",
        population: 150837,
        area: 3216,
        imageUrl: "assets/files/img/regions/BG-09.gif"
      },
      {
        regionId: "BG-10",
        name: "Кюстендил",
        population: 123431,
        area: 3084.3,
        imageUrl: "assets/files/img/regions/BG-10.gif"
      },
      {
        regionId: "BG-11",
        name: "Ловеч",
        population: 129222,
        area: 4132,
        imageUrl: "assets/files/img/regions/BG-11.gif"
      },
      {
        regionId: "BG-12",
        name: "Монтана",
        population: 134669,
        area: 3635,
        imageUrl: "assets/files/img/regions/BG-12.gif"
      },
      {
        regionId: "BG-13",
        name: "Пазарджик",
        population: 260814,
        area: 4459,
        imageUrl: "assets/files/img/regions/BG-13.gif"
      },
      {
        regionId: "BG-14",
        name: "Перник",
        population: 123770,
        area: 2392,
        imageUrl: "assets/files/img/regions/BG-14.gif"
      },
      {
        regionId: "BG-15",
        name: "Плевен",
        population: 248138,
        area: 4337,
        imageUrl: "assets/files/img/regions/BG-15.gif"
      },
      {
        regionId: "BG-16",
        name: "Пловдив",
        population: 671573,
        area: 5972.89,
        imageUrl: "assets/files/img/regions/BG-16.gif"
      },
      {
        regionId: "BG-17",
        name: "Разград",
        population: 115402,
        area: 2637,
        imageUrl: "assets/files/img/regions/BG-17.gif"
      },
      {
        regionId: "BG-18",
        name: "Русе",
        population: 223489,
        area: 2791,
        imageUrl: "assets/files/img/regions/BG-18.gif"
      },
      {
        regionId: "BG-19",
        name: "Силистра",
        population: 111957,
        area: 2846,
        imageUrl: "assets/files/img/regions/BG-19.gif"
      },
      {
        regionId: "BG-20",
        name: "Сливен",
        population: 189788,
        area: 3544.066,
        imageUrl: "assets/files/img/regions/BG-20.gif"
      },
      {
        regionId: "BG-21",
        name: "Смолян",
        population: 109425,
        area: 3193,
        imageUrl: "assets/files/img/regions/BG-21.gif"
      },
      {
        regionId: "BG-23",
        name: "София-Град",
        population: 1291591,
        area: 1348.902,
        imageUrl: "assets/files/img/regions/BG-22.gif"
      },
      {
        regionId: "BG-22",
        name: "София",
        population: 247489,
        area: 7059,
        imageUrl: "assets/files/img/regions/BG-23.gif"
      },
      {
        regionId: "BG-24",
        name: "Стара Загора",
        population: 333265,
        area: 5152,
        imageUrl: "assets/files/img/regions/BG-24.gif"
      },
      {
        regionId: "BG-25",
        name: "Търговище",
        population: 120818,
        area: 2558.5,
        imageUrl: "assets/files/img/regions/BG-25.gif"
      },
      {
        regionId: "BG-26",
        name: "Хасково",
        population: 246238,
        area: 5543,
        imageUrl: "assets/files/img/regions/BG-26.gif"
      },
      {
        regionId: "BG-27",
        name: "Шумен",
        population: 180528,
        area: 3389.7,
        imageUrl: "assets/files/img/regions/BG-27.gif"
      },
      {
        regionId: "BG-28",
        name: "Ямбол",
        population: 123821,
        area: 3335.5,
        imageUrl: "assets/files/img/regions/BG-28.gif"
      }
    ];

    const facts: Fact[] = [
      {
        id: 1,
        title: "Името България и град Плиска",
        readMore: "https://bg.wikipedia.org/wiki/%D0%9F%D0%BB%D0%B8%D1%81%D0%BA%D0%B0",
        description: "България е най-старата държава в Европа, която не е променяла името си след своето основаване през 681 г. Първата столица на Дунавска България - Плиска се намира в община Каспичан, област Шумен.",
        type: Type.HISTORY,
        regionId: "BG-27",
        imageUrl: "assets/files/img/facts/bulgaria.png",
        videoId: "PBVQsEMx1Ng",
      },
      {
        id: 2,
        title: "Дан Колов",
        readMore: "https://bg.wikipedia.org/wiki/%D0%94%D0%B0%D0%BD_%D0%9A%D0%BE%D0%BB%D0%BE%D0%B2",
        description: "Първият борец в света с 1500 победи и 3 загуби е българинът Дончо Колев Данев, по-известен като Дан Колов. Хората го наричат също Кинг Конг, Царят на борбата и Българският лъв. Дан Колов е родом от с. Сенник, община Севлиево, област Габрово.",
        type: Type.HISTORY,
        regionId: "BG-07",
        imageUrl: "assets/files/img/facts/dan_kolov.jpg"
      },
      {
        id: 3,
        title: "Розово масло",
        readMore: "https://epis.bg/razni/nepovtorimoto-balgarsko-rozovo-maslo/",
        description: "Българското розово масло се използва за направата на едни от най-известните марки парфюми. За един грам розово масло са нужни 1000 розови венчелистчета. Град Казанлък, считан за столица на розовата долина се намира в област Стара Загора.",
        type: Type.NATURE,
        regionId: "BG-24",
        imageUrl: "assets/files/img/facts/rose_oil.jpg",
        videoId: "tR4o9zlT1Ow",
      },
      {
        id: 4,
        title: "Четиньова могила",
        readMore: "https://bulgariatravel.org/bg/%d1%82%d1%80%d0%b0%d0%ba%d0%b8%d0%b9%d1%81%d0%ba%d0%b8-%d1%85%d1%80%d0%b0%d0%bc%d0%be%d0%b2-%d0%ba%d0%be%d0%bc%d0%bf%d0%bb%d0%b5%d0%ba%d1%81-%d1%81%d0%b5%d0%bb%d0%be-%d1%81%d1%82%d0%b0%d1%80%d0%be/",
        description: "Над 15 000 тракийски гробници са открити на българска територия, повечето от тях са все още неизследвани. Една от тях е гробницата в село Старосел, община Хисаря, област Пловдив.",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/starosel.jpg",
        videoId: "xx4iUCL9OzE",
      },
      {
        id: 5,
        title: "Най-старото обработено злато",
        readMore: "https://visit.varna.bg/bg/sights/preview/293.html",
        description: "Златното съкровище, открито във Варненския некропол е най-старото в света. То датира от 4400 г. преди Христа. То се смята и за най-старото обработено злато.",
        type: Type.HISTORY,
        regionId: "BG-03",
        imageUrl: "assets/files/img/facts/necropolis.jpg"
      },
      {
        id: 6,
        title: "Археологични открития",
        readMore: "https://www.flashnews.bg/10-fakta-za-panagyurskoto-zlatno-sakrovishte/",
        description: "България е третата страна в Европа с най-ценните археологични открития на своя територия. Преди нея са Гърция и Италия. Най-известното, Панагюрско златно съкровище, е открито на 2 км от гр. Панагюрище, област Пазарджик.",
        type: Type.HISTORY,
        regionId: "BG-13",
        imageUrl: "assets/files/img/facts/panagyursko-sakrovishte.jpg"
      },
      {
        id: 7,
        title: "Хилядолетна София",
        readMore: "https://www.sofia.bg/web/tourism-in-sofia/history-of-sofia",
        description: "Столицата на България - София съществува от 7000 години, което я прави един от най-старите градове в Европа.",
        type: Type.HISTORY,
        regionId: "BG-23",
        imageUrl: "assets/files/img/facts/saint_sofia.jpg",
        videoId: "seiQLbch2Fs",
      },
      {
        id: 8,
        title: "Разходка във Витоша",
        readMore: "http://www.navitosha.bg/%D0%BF%D0%BE%D0%B4%D1%85%D0%BE%D0%B4%D1%8F%D1%89%D0%B8-%D0%BC%D0%B5%D1%81%D1%82%D0%B0-%D0%B7%D0%B0-%D1%80%D0%B0%D0%B7%D1%85%D0%BE%D0%B4%D0%BA%D0%B0-%D0%BD%D0%B0-%D0%B2%D0%B8%D1%82%D0%BE%D1%88%D0%B0/",
        description: "София е единственият голям град в Европа, който се намира на 15 минути от планина. Ето защо Витоша е любимо място за отдих за много от гражданите й.",
        type: Type.NATURE,
        regionId: "BG-23",
        imageUrl: "assets/files/img/facts/sofia_vitosha.jpg"
      },
      {
        id: 9,
        title: "Битката при Клокотница",
        readMore: "https://bulgarianhistory.org/klokotnica-bitka-ivan-asen/",
        description: "Българската армия никога не е губила флаг в битка, въпреки множеството войни, в които е участвала. Такава е и битката при Клокотница, област Хасково, където през 1230г. Иван Асен II побеждава убедително Теодор Комнин.",
        type: Type.HISTORY,
        regionId: "BG-26",
        imageUrl: "assets/files/img/facts/klokotnica.jpg"
      },
      {
        id: 10,
        title: "Българска песен в космоса",
        readMore: "http://bnr.bg/horizont/post/100932063/istoriata-na-edna-pesen-izlel-e-delyo-haidutin",
        description: "След изстрелването на Вояджър 1 и 2 през 1977г., българската народна песен \"Излел е Делю Хайдутин\", изпълнявана от Валя Балканска, звучи в космоса заедно с най-великите творби на Бах и Моцарт. Валя Балканска е родом от с. Арда, област Смолян.",
        type: Type.HISTORY,
        regionId: "BG-21",
        imageUrl: "assets/files/img/facts/valya_balkanska.jpg"
      },
      {
        id: 11,
        title: "Компютър на Атанасов-Бери",
        readMore: "https://www.chr.bg/istorii/tehno/dzhon-atanasov-istinskiyat-sazdatel-na-kompyutara/",
        description: "Първият компютър е дело на български потомък. В периода 1937-1942г. Джон Атанасов, син на българин и ирландка заедно с Клифърд Бери създават първия дигитален компютър. Бащата на Джон Атанасов - Иван Атанасов е родом от област Ямбол",
        type: Type.HISTORY,
        regionId: "BG-28",
        imageUrl: "assets/files/img/facts/john_atanasoff.jpg"
      },
      {
        id: 12,
        title: "Петър Петров",
        readMore: "http://bnr.bg/radiobulgaria/post/100274167/petyr-petrov-edin-ot-nai-produktivnite-izobretateli-na-hh-vek",
        description: "Първият цифров часовник е дело на българин. Името му е Петър Петров, който е родом от с. Брестовица, област Пловдив",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/petar_petrov.jpg",
        videoId: "0ihT1Ce4Rck"
      },
      {
        id: 13,
        title: "Киселото мляко",
        readMore: "https://bulgarianhistory.org/kiseloto-mlqko/",
        description: "Несъмнено, българското кисело мляко е единствено по рода си. Това се дължи на бактерията Lactobacillus Bulgaricus, открита от Стамен Григоров през 1905г. Стамен Григоров е български микробиолог и лекар, родом от с. Студен Извор, област Перник.",
        type: Type.NATURE,
        regionId: "BG-14",
        imageUrl: "assets/files/img/facts/yoghurt.jpg"
      },
      {
        id: 14,
        title: "Евксиноград",
        readMore: "https://bulgarianhistory.org/evksinovgrad/",
        description: "Съществуват солидни доказателства за това, че вино се произвежда на българска територия от самата каменна епоха. През годините България се е доказала като световен производител на вино. Най-старата винарна на име Евксиноград се намира в област Варна.",
        type: Type.HISTORY,
        regionId: "BG-03",
        imageUrl: "assets/files/img/facts/evksinograd.jpg"
      },
      {
        id: 15,
        title: "Минерални бани",
        readMore: "http://www.nasamnatam.com/statia/Sapareva_bania_nai_goreshtite_mineralni_izvori_v_ciala_Evropa-2276.html",
        description: "България е страната с най-голям брой естествени минерални извори в централна Европа - над 600. В град Сапарева Баня, област Кюстендил се намират на горещите от тях.",
        type: Type.NATURE,
        regionId: "BG-10",
        imageUrl: "assets/files/img/facts/hisaria.jpg"
      },
      {
        id: 16,
        title: "Райна Касабова",
        readMore: "https://bulgarianhistory.org/raina-kasabova/",
        description: "Райна Касабова е първата жена, която взема участие във военен полет. На 30 октомври 1912г., на 15 годишна възраст, по време на Първата Балканска война, тя се присъединява към Българските ВВС и лети над Одрин като разпръсква пропагандни писма на турски. Ледникът Касабова на бряг Дейвис в Земя Греъм на Антарктическия полуостров е именуван на Райна Касабова.",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/rayna_kasabova.jpg"
      },
      {
        id: 17,
        title: "Иван Ценов",
        readMore: "http://mmib.math.bas.bg/?page_id=1344",
        description: "Българите заемат второ място в света на международния IQ тест. Пример за това е математикът Иван Ценов, родом от Враца.",
        type: Type.HISTORY,
        regionId: "BG-06",
        imageUrl: "assets/files/img/facts/Ivan_Cenov.jpg"
      },
      {
        id: 18,
        title: "Рафаилов кръст",
        readMore: "https://www.lifebites.bg/rafailoviat-kryst/",
        description: "Рафаиловият кръст, съхраняван в Рилския манастир, област Кюстендил, е кръст с повече от 100 микроскопични сцени от Библията. Това е едно от най-деликатните произведения на изкуството в света. За направата му са били необходими 12 години.",
        type: Type.HISTORY,
        regionId: "BG-10",
        imageUrl: "assets/files/img/facts/cross.jpg"
      },
      {
        id: 19,
        title: "Димитър Пешев",
        readMore: "https://bulgarianhistory.org/pismo-evrei/",
        description: "Димитър Пешев е български политик родом от Кюстендил, чието протестно писмо изиграва важна роля за спасяването на българските евреи по време на Холокоста, освен България единствено Дания също спасява своите евреи.",
        type: Type.HISTORY,
        regionId: "BG-10",
        imageUrl: "assets/files/img/facts/Dimitar_Peshev.jpg"
      },
      {
        id: 20,
        title: "Лавандулово масло",
        readMore: "https://www.webcafe.bg/id_1769139282",
        description: "Преди години България е била малко зад лидера по износ на лавандулово масло - Франция. Българското лавандулово масло има траен, фин аромат и е достоен конкурент сред останалите масла в света. В област Добрич има близо 72 200 декара лавандулови насаждения.",
        type: Type.NATURE,
        regionId: "BG-19",
        imageUrl: "assets/files/img/facts/lavender.jpg"
      },
      {
        id: 21,
        title: "Лонгоз",
        readMore: "http://www.nasamnatam.com/statia/Rezervat_Kamchiia_i_nai_goliamata_vekovna_krairechna_gora_v_Bylgariia-2406.html",
        description: "Над една трета от територията на България се състои от гори. В края на 2013г. това са около 37.7% според Министерството на земеделието, храните и горите. Край река Камчия, област Варна се намира най-голямата вековна гора на име Лонгоз.",
        type: Type.NATURE,
        regionId: "BG-03",
        imageUrl: "assets/files/img/facts/forest.jpg"
      },
      {
        id: 22,
        title: "Древен български календар",
        readMore: "https://historybg.info/%D0%B4%D1%80%D0%B5%D0%B2%D0%B5%D0%BD-%D0%B1%D1%8A%D0%BB%D0%B3%D0%B0%D1%80%D1%81%D0%BA%D0%B8-%D0%BA%D0%B0%D0%BB%D0%B5%D0%BD%D0%B4%D0%B0%D1%80/",
        description: "През 1976г. Древният български календар е обявен за най-точен в света от ЮНЕСКО. Вариант на календара може да бъде видян в централния парк в град Разлог, област Благоевград.",
        type: Type.HISTORY,
        regionId: "BG-01",
        imageUrl: "assets/files/img/facts/calendar.png"
      },
      {
        id: 23,
        title: "Стефка Костадинова",
        readMore: "https://www.24chasa.bg/sport/article/6419794",
        description: "Стефка Костадинова, родом от Пловдив, е жената с най-висок скок на планетата. На 30 Август 1987г. по време на световно първенство по лека атлетика в Рим, тя поставя уникалния световен рекорд във висок скок - 209см. Рекордът не е подобрен и до днес.",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/jump.jpg",
        videoId: "vCSWOruPXiQ"
      },
      {
        id: 24,
        title: "Йордан Йовчев",
        readMore: "https://www.bgonair.bg/a/149-deset/53081-deset-saveta-za-uspeh-v-sporta-ot-yordan-yovchev",
        description: "Невероятният Йордан Йовчев от Пловдив е единственият гимнастик в историята, който участва шест пъти в Олимпийски игри. Той е четирикратен световен шампион и европейски шампион.",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/yordan_yovchev.jpg",
        videoId: "PQJ42eDcVuQ"
      },
      {
        id: 25,
        title: "Христо Явашев - Кристо",
        readMore: "https://christojeanneclaude.net/projects/wrapped-reichstag",
        description: "През последните няколко години арт техника наречена \"опаковане\" придоби значителна популярност. Неин автор е, роденият в Габрово, Христо Явашев - Кристо. Едни от най-известните му проекти са опаковането на Райхстага в Берлин и Понт Ньов в Париж.",
        type: Type.HISTORY,
        regionId: "BG-07",
        imageUrl: "assets/files/img/facts/reichstag.jpg"
      },
      {
        id: 26,
        title: "Българската гайда",
        readMore: "http://bnr.bg/post/101081709",
        description: "Каба гайдата е един от най-отличаващите се символи на народната музика в България. Нейният репертоар съдържа тонове и песни от девни времена. Естествените материали, използвани за направата й и начинът на изработка са причините за отличителнияй глас и вибрация. На снимката: Дафо Трендафилов (1919 - 2010) е музикант, майстор-гайдар и преподавател от село Гела, област Смолян.",
        type: Type.HISTORY,
        regionId: "BG-21",
        imageUrl: "assets/files/img/facts/Bai_Dafo.jpg"
      },
      {
        id: 27,
        title: "Мъжко хоро",
        readMore: "http://kalofer.bg/?p=799",
        description: "Всяка година на Йордановден в град Калофер, област Пловдив се провежда традиционното мъжко хоро в ледените води на река Тунджа.",
        type: Type.HISTORY,
        regionId: "BG-16",
        imageUrl: "assets/files/img/facts/kalofer_horo.jpg"
      },
      {
        id: 28,
        title: "Бащата на авиацията",
        readMore: "https://bulgarianhistory.org/bashtata-na-aviatsiyata-asen-jordanov/",
        description: "Първата въздушна възглавница в света, предназначена да предпазва пилоти и автомобилисти е създадена през 1957г. от българина Асен Йорданов от София.",
        type: Type.HISTORY,
        regionId: "BG-23",
        imageUrl: "assets/files/img/facts/asen_yordanov.png"
      },
      {
        id: 29,
        title: "Духлата",
        readMore: "https://www.bulgariatravel.org/article/details/84#map=6/42.750/25.380",
        description: "Най-дългата пещера (над 15 км) на име \"Духлата\" е близо до село Боснек, област Перник. Шест подземни реки са издълбали лабиринти в нейните галерии. Около 4000 пещери са изследвани и картографирани в България.",
        type: Type.NATURE,
        regionId: "BG-14",
        imageUrl: "assets/files/img/facts/cave.jpg"
      }
    ];

    const poi: Poi[] = [
      {
        id: 30,
        title: "Пещера Козарника",
        address: "",
        description: "Разположена е в скалите на около 3,5 km от село Гара Орешец по посока град Белоградчик. От отбивка на пътя се тръгва по стръмна пътека нагоре и до пещерата се върви около 250 метра. В близост се намира и електрифицираната пещера Венеца. Нейната обща дължина е 210 метра.",
        type: Type.NATURE,
        latitude: 43.651859,
        longitude: 22.7023029,
        regionId: "BG-05",
        imageUrl: "assets/files/img/poi/kozarnika.jpg",
        readMore: "http://www.nasamnatam.com/statia/Peshterata_Kozarnika_izkliuchitelna_praistoricheska_nahodka_za_Bylgariia_i_Evropa-2260.html"
      },
      {
        id: 31,
        title: "Варненската морска градина",
        address: "",
        description: "Морската градина е най-големият подобен парк на Балканите и е обявена за произведение на парковото изкуство. Посещавана от милиони всяка година, градината е също така предпочитана от местните като място за разходка, раздумка, спорт и културни занимания.",
        type: Type.NATURE,
        latitude: 43.207901,
        longitude: 27.9316062,
        regionId: "BG-03",
        imageUrl: "assets/files/img/poi/varna-sea-garden.jpg",
        readMore: "https://visit.varna.bg/bg/parks_gardens/preview/36.html"
      },
      {
        id: 32,
        title: "Язовир Огоста",
        address: "",
        description: "Язовир Огоста се намира в Северозападна България и е четвъртият по площ и вторият по обем изкуствен водоем в страната. Заема площ от 23,6 кв. км, а общият му обем е 506 млн. куб. м. През 1999 г. водоемът е определен за подходящ за промишлен риболов. Има голямо разнообразие на риба - шаран, каракуда, червеноперка, платика, костур, скобар, мряна, сом, щука.",
        type: Type.NATURE,
        latitude: 43.3729681,
        longitude: 23.188985,
        regionId: "BG-12",
        imageUrl: "assets/files/img/poi/yaz_ogosta.jpg",
        readMore: "http://zavodata.com/2014/01/%D1%8F%D0%B7%D0%BE%D0%B2%D0%B8%D1%80-%E2%80%9E%D0%BE%D0%B3%D0%BE%D1%81%D1%82%D0%B0%E2%80%9C-%E2%80%93-%D0%BE%D0%BF%D0%B0%D1%81%D0%BD%D0%B8%D1%8F%D1%82-%D1%85%D0%B8%D0%B4%D1%80%D0%BE%D0%B3%D0%B8%D0%B3/"
      },
      {
        id: 33,
        title: "Водопад Скакля",
        address: "",
        description: "Скакля или Врачанска Скакля е водопад във Врачанския Балкан. Той е най-високият непостоянно течащ водопад в България и на Балканите – 141 m. Намира се на 1,5 km южно от Враца, зад хълма Калето. В района му са открити останки от средновековното българско селище Патлейна. Красив през всички сезони, от горната му част се откриват прекрасни гледки към Враца и Врачанското поле.",
        type: Type.NATURE,
        latitude: 43.1853381,
        longitude: 23.5474441,
        regionId: "BG-06",
        imageUrl: "assets/files/img/poi/skaklya.jpg",
        readMore: "https://drumivdumi.com/%D0%B0%D0%BA%D1%82%D0%B8%D0%B2%D0%B5%D0%BD-%D1%82%D1%83%D1%80%D0%B8%D0%B7%D1%8A%D0%BC-%D0%B2-%D0%B8%D1%81%D0%BA%D1%8A%D1%80%D1%81%D0%BA%D0%BE%D1%82%D0%BE-%D0%B4%D0%B5%D1%84%D0%B8%D0%BB%D0%B5-%D0%B2/"
      },
      {
        id: 34,
        title: "Панорама \"Плевенска епопея 1877г.\"",
        address: "Скобелев парк, 5804 Скобелев",
        description: "Панорамата „Плевенска епопея 1877 г.“ е художествен музей в Плевен, построен в чест на 100-годишнината от Освобождението на Плевен от османско владичество. В първите 3 години след нейното откриване панорамата е посетена от 2,5 милиона души.",
        type: Type.HISTORY,
        latitude: 43.3987508,
        longitude: 24.6061726,
        regionId: "BG-15",
        imageUrl: "assets/files/img/poi/panorama_pleven.jpg",
        readMore: "https://www.pleven.bg/bg/zabelezhitelnosti-pametnitsi-i-muzei/panorama-plevenska-epopeya-1877-g/"
      },
      {
        id: 35,
        title: "Парк с макети на исторически забележителности - \"Търновград - духът на хилядолетна България\"",
        address: "ул. „Просвета“, 5000 Царевец",
        description: "Паркът е разположен на терен от около 12дка. и се намира точно под Балдуиновата кула на Царевец в местността Френк Хисар. Миниатюрите са изработени в мащаб 1:25, от съвременни пластмаси, които са предназначени за излагане на открито. Сред макетите са почти всички най-известни и любими исторически забележителности на страната - крепостта Царевец и паметникът \"Асеневци\" във Велико Търново, Античният театър в Пловдив, катедралата \"Успение Богоридично\" във Варна, храм-паметникът \"Александър Невски\" в София, крепостите Баба Вида и Асенова, паметникът \"Шипка\", храмове в Несебър, Рилския манастир и този в Бачково и др.",
        regionId: "BG-04",
        type: Type.HISTORY,
        latitude: 43.0798848,
        longitude: 25.6542902,
        imageUrl: "assets/files/img/poi/miniatures.jpg",
        readMore: "http://www.bta.bg/bg/video/show/id/0_591f3bkt"
      },
      {
        id: 36,
        title: "Природен парк \"Русенски Лом\"",
        address: "",
        description: "Природен парк Русенски Лом се намира по поречието на река Русенски Лом, която преминава през живописни местности. Тук се намират Ивановските скални църкви, средновековният град Червен край Червен и единственият в България действащ скален манастир край село Басарбово.",
        regionId: "BG-18",
        type: Type.NATURE,
        latitude: 43.7041616,
        longitude: 25.976197,
        imageUrl: "assets/files/img/poi/rusenski_lom.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/93"
      },
      {
        id: 37,
        title: "Свещарска гробница",
        address: "Община Исперих, на 2,5 км югозападно от село Свещари",
        description: "Разкрита е през 1982 г. при разкопаването на високата Гинина могила край селото. Представлява тракийско-елинистична гробница от първата половина на III в. пр.н.е. Това е царска гробница, в която вероятно е погребан гетският владетел Дромихет. Изградена е от гладко обработени каменни блокове от мек варовик.",
        regionId: "BG-17",
        type: Type.HISTORY,
        latitude: 43.7450757,
        longitude: 26.7663873,
        imageUrl: "assets/files/img/poi/sveshtarska_grobnica.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/69"
      },
      {
        id: 38,
        title: "Резерват Сребърна",
        address: "край село Сребърна, на 16 км западно от Силистра и на 2 км южно от Дунав",
        description: "„Сребърна“ е поддържан биосферен резерват в България, обект на световното наследство на ЮНЕСКО. Местността е обявена за резерват през 1948 г. с охраняема площ от около 892,05 ha, както и буферна зона от около 540 ha. Обхваща езерото Сребърна и неговите околности. Намира се на главното миграционно трасе на прелетните птици между Европа и Африка, наречено „Via Pontica“.",
        regionId: "BG-19",
        type: Type.NATURE,
        latitude: 44.1154234,
        longitude: 27.0718391,
        imageUrl: "assets/files/img/poi/srebarna.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/29"
      },
      {
        id: 39,
        title: "Яйлата",
        address: "На 2 км южно от Камен бряг и на 18 км североизточно от Каварна",
        description: "Яйлата (високо пасище) е национален археологически резерват в област Добрич. Представлява приморска тераса с площ 300 декара, отделена от морето от скални масиви с височина 50 – 60 m. Местността Яйлата е обявена за археологически резерват с решение на Министерския съвет през 1989 г.",
        regionId: "BG-08",
        type: Type.HISTORY,
        latitude: 43.4352535,
        longitude: 28.5419001,
        imageUrl: "assets/files/img/poi/qilata.jpg",
        readMore: "http://kamenbryag.info/yailata/"
      },
      {
        id: 40,
        title: "Скален манастир \"Монасите\"",
        address: "",
        description: "Манастирът се състои се от три верижно свързани помещения с правоъгълна форма, ориентирани в посока север-юг. Те са с размери 2м на 3м и с височина 1,8 м. Входът на килиите е от югозапад. В западната стена на втората е изсечен прозорец, а в третата — две скамейки. Според предание, манастирът се е наричал \"Св. Благовещение\". В него са намерени византийски монети от династията на Комнините (ХI-ХIIв). В подножието на манастира е изградено място за отдих и почивка с навес, дървени пейки и масички, а край тях има барбекю.",
        regionId: "BG-03",
        type: Type.HISTORY,
        latitude: 43.0959066,
        longitude: 27.676798,
        imageUrl: "assets/files/img/poi/monasite.jpg",
        readMore: ""
      },
      {
        id: 41,
        title: "Мадарски конник",
        address: "село Мадара, Шумен",
        description: "Ма̀дарският конник е средновековен барелеф, изсечен на отвесна скала близо до днешното село Мадара в Североизточна България. Това е единственият скален релеф в Европа от периода на ранното средновековие и е обявен от ЮНЕСКО за паметник на световното културно наследство.",
        regionId: "BG-27",
        type: Type.HISTORY,
        latitude: 43.2772591,
        longitude: 27.1191988,
        imageUrl: "assets/files/img/poi/konnik.jpg",
        readMore: "https://bulgarianhistory.org/madarski-konnik-hipotezi-istoria/"
      },
      {
        id: 42,
        title: "Ковачевско кале",
        address: "на 6 км западно от гр. Попово",
        description: "Ковачевското кале е късноримска крепост, която първоначално става известна на науката, благодарение на чешкия археолог Карел Шкорпил под името „Ковачовешко кале“, поради тогавашния изговор на най-близкото до нея село, наричано тогава „Ковачовец“.",
        regionId: "BG-25",
        type: Type.HISTORY,
        latitude: 43.3590825,
        longitude: 26.1452022,
        imageUrl: "assets/files/img/poi/kale.jpg",
        readMore: "http://gotargo.com/destination/kovachevskoto-kale-popovo/"
      },
      {
        id: 43,
        title: "Етнографски музей на открито \"Етър\"",
        address: "ул. „Генерал Дерожински“ 144, 5309 Габрово",
        description: "Етъра представлява възстановка на българския бит, култура и занаятчийство. Той е първият по рода си музей в България. Открит е на 7 септември 1964 г. Музеят е разположен на 8 км южно от основната част на Габрово.",
        regionId: "BG-07",
        type: Type.HISTORY,
        latitude: 42.8034801,
        longitude: 25.3491946,
        imageUrl: "assets/files/img/poi/etura.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/6"
      },
      {
        id: 44,
        title: "Пещера Проходна (Очите на Бога)",
        address: "",
        description: "Проходна е една от най-известните пещери в България, намираща се в непосредствена близост до село Карлуково (на 0,5км.), емблема на Карлуковският карстов район. Геоложкият феномен се намира на 250 метра надморска височина. Пещерата е леснодостъпна и проходима през всички сезони от годината.",
        regionId: "BG-11",
        type: Type.NATURE,
        latitude: 43.1750071,
        longitude: 24.0739669,
        imageUrl: "assets/files/img/poi/eyes_of_god.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/199"
      },
      {
        id: 45,
        title: "Искърски пролом",
        address: "",
        description: "Искърският пролом (или Старопланински пролом на река Искър) е пролом на река Искър в Западна България, в Западна Стара планина, в Софийска област и област Враца. Свързва Софийската котловина на юг с Дунавската равнина на север. Искърският пролом е най-дългият и най-впечатляващият пролом в България с дължина от 84 km и средна надморска височина от 362 m.",
        regionId: "BG-22",
        type: Type.NATURE,
        latitude: 43.0435634,
        longitude: 23.3529482,
        imageUrl: "assets/files/img/poi/iskar_prolom.jpg",
        readMore: "http://www.nasamnatam.com/statia/Jivopisniiat_Iskyrski_prolom-2422.html"
      },
      {
        id: 46,
        title: "Златните мостове",
        address: "",
        description: "Златните мостове е местност в планината Витоша, България, която представлява най-голямата каменна река в района. Разположена е на надморска височина от 1350 до 1500 метра в поречието на Владайската река. Името идва от лишеите със златист цвят, които покриват големите камъни.",
        regionId: "BG-23",
        type: Type.NATURE,
        latitude: 42.6096115,
        longitude: 23.2390451,
        imageUrl: "assets/files/img/poi/zlatni_mostove.jpg",
        readMore: "https://svetogled.com/zlatnite-mostove-do-boerica-vitosha-s-deca/"
      },
      {
        id: 47,
        title: "Земенски манастир \"Свети Йоан Богослов\"",
        address: "",
        description: "Земенският манастир „Свети Йоан Богослов“ е български манастир, основан през 11 век. Разположен е на около 1 км югоизточно от град Земен – един от най-малките градове в България.",
        regionId: "BG-14",
        type: Type.HISTORY,
        latitude: 42.4677108,
        longitude: 22.7381402,
        imageUrl: "assets/files/img/poi/zemenski_manastir.jpg",
        readMore: "http://www.bulgariamonasteries.com/zemenski_manastir.html"
      },
      {
        id: 48,
        title: "Къща-музей Ильо Войвода",
        address: "бул. „Цар Освободител“ 189, 2500 Герена, Кюстендил",
        description: "Къщата-музей Ильо Войвода е музейна сбирка към Историческия музей в Кюстендил. Къщата е построена около 1870 г., а по-късно в нея живее един от най-бележитите революционери - Ильо Войвода. През 1980 г. къщата е реставрирана и година след това е официално отворен музея.",
        regionId: "BG-10",
        type: Type.HISTORY,
        latitude: 42.2821633,
        longitude: 22.6973683,
        imageUrl: "assets/files/img/poi/Ilyo-voyvoda-ka6ta.jpg",
        readMore: "https://opoznai.bg/view/kashta-muzei-ilio-voivoda-kiustendil"
      },
      {
        id: 49,
        title: "Хераклея Синтика",
        address: "ул. „Бългaрия“ 0, Петрич",
        description: "Хераклея Синтика е античен град в днешна Югозападна България. Руините му са разположени в землището на село Рупите, община Петрич, върху южния склон и подножието на Джонков връх. В древността градът е бил център на областта Синтика, населявана от тракийското племе синти, за което споменават Омир, Херодот и Тукидид.",
        regionId: "BG-01",
        type: Type.HISTORY,
        latitude: 41.450833,
        longitude: 23.262778,
        imageUrl: "assets/files/img/poi/herakleya_sintika.jpg",
        readMore: "http://www.nasamnatam.com/statia/Herakleia_Sintika_tainstveniiat_trakiiski_grad_krai_Petrich-2746.html"
      },
      {
        id: 50,
        title: "Крепост Перистера",
        address: "Стара крепост, 4550 Пещера",
        description: "Крепостта Перистера се намира на север от град Пещера. Името на крепостта произхожда от гръцки и означава „гълъб“. Представлява крепост с три пояса крепостни стени и шест отбранителни кули, разположени на най-вътрешната крепостна стена, според археолозите е датирана към 4 век, като е съществувала поне до 7 век включително. Археолозите смятат, че на същото място вероятно е съществувало древно тракийско светилище.",
        regionId: "BG-13",
        type: Type.HISTORY,
        latitude: 42.0388296,
        longitude: 24.304242,
        imageUrl: "assets/files/img/poi/peristera.jpg",
        readMore: "https://www.btsbg.org/100nto/krepost-peristera"
      },
      {
        id: 51,
        title: "Античен театър",
        address: "ул. Цар Ивайло 4",
        description: "Античният театър на Филипополис е сред най-добре запазените антични театри в света и сред основните туристически атракции на Пловдив. Построен е по времето на римския император Марк Улпий Траян сл. Хр. и е разкрит при археологически разкопки от 1968 до 1979 г. от Археологически музей Пловдив.",
        regionId: "BG-16",
        type: Type.HISTORY,
        latitude: 42.1468859,
        longitude: 24.7510692,
        imageUrl: "assets/files/img/poi/antichen_teatur.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/7"
      },
      {
        id: 52,
        title: "Казанлъшка гробница",
        address: "ул. „Генерал Радецки“, 6102 Партюлбе, Казанлък",
        description: "Тракийската гробница в Казанлък е зидана кръглокуполна гробница, част е от голям некропол, разположен в близост до древната столица на Одриското царство Севтополис. Датирана е в края на IV в. пр. Хр. – началото на III в. пр.",
        regionId: "BG-24",
        type: Type.HISTORY,
        latitude: 42.6258404,
        longitude: 25.3991644,
        imageUrl: "assets/files/img/poi/kazanlushka_grobnica.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/14"
      },
      {
        id: 53,
        title: "Скално образувание Халката",
        address: "",
        description: "Кварцовото скално образувание Халката е типичен представител на интересните и красиви скални форми в природен парк \"Сините камъни\". Резултат е от рушителното действие на атмосферните фактори - слънцето, вятърът, водата. Височината на Халката е повече от 8 метра. Човек може спокойно да премине през отвора й с височина около 2 метра.",
        regionId: "BG-20",
        type: Type.NATURE,
        latitude: 42.7083624,
        longitude: 26.3495862,
        imageUrl: "assets/files/img/poi/halkata.jpg",
        readMore: "https://opoznai.bg/view/skalno-obrazuvanie-halkata-sliven"
      },
      {
        id: 54,
        title: "Резерват за водни кончета",
        address: "село Дебелт",
        description: "Резерватът за водни кончета се намира на юг от Бургас, в близост до село Дебелт. Мястото е заградено и през пролетта се виждат множество водни кончета. Има малка влажна зона и навес, но без пейки. Мястото е подходящо за отдих. Има информационни табели.",
        regionId: "BG-02",
        type: Type.NATURE,
        latitude: 42.3917743,
        longitude: 27.2673627,
        imageUrl: "assets/files/img/poi/vodni_koncheta.jpg",
        readMore: "http://burgaslakes.org/tour/bg/dragonfly-reserve.html"
      },
      {
        id: 55,
        title: "Древен град Кабиле",
        address: "село Кабиле",
        description: "Кабиле е древен тракийски град, основан около IV в. пр. Хр. върху основите на по-древно селище в подножието на днешния Зайчи връх, близо до завоя на река Тонзос (Тунджа) в древността. В наши дни територията, на която е бил разположен древния град и по-късните селища край него са превърнати в археологически резерват, разположен на 10 километра от град Ямбол.",
        regionId: "BG-28",
        type: Type.HISTORY,
        latitude: 42.5473271,
        longitude: 26.4841647,
        imageUrl: "assets/files/img/poi/kabile.jpg",
        readMore: "http://yambolmuseum.eu/%D1%82%D1%80%D0%B0%D0%BA%D0%B8%D0%B9%D1%81%D0%BA%D0%B8-%D0%B8-%D0%B0%D0%BD%D1%82%D0%B8%D1%87%D0%B5%D0%BD-%D0%B3%D1%80%D0%B0%D0%B4-%D0%BA%D0%B0%D0%B1%D0%B8%D0%BB%D0%B5/"
      },
      {
        id: 56,
        title: "Вила Армира",
        address: "",
        description: "Вила „Армира“ е антична римска вила в близост до Ивайловград. Днес тя е паметник на културата с национално значение. От 2014 г. е под номер 72б в списъка „100 национални туристически обекта“ на Българския туристически съюз.",
        regionId: "BG-26",
        type: Type.HISTORY,
        latitude: 41.4991444,
        longitude: 26.106491,
        imageUrl: "assets/files/img/poi/vila_armira.jpg",
        readMore: "https://drumivdumi.com/%D0%B2%D0%B8%D0%BB%D0%B0-%D0%B0%D1%80%D0%BC%D0%B8%D1%80%D0%B0-%D0%B4%D0%BE-%D0%B8%D0%B2%D0%B0%D0%B9%D0%BB%D0%BE%D0%B2%D0%B3%D1%80%D0%B0%D0%B4/"
      },
      {
        id: 57,
        title: "Скален феномен \"Каменна сватба\"",
        address: "",
        description: "Природният феномен Каменна сватба се намира на 5 км източно от град Кърджали, край село Зимзелен. Разположен е на площ от около 40 дка и е обявен за защитен обект през 1974 г. Наред с намиращите се край село Бели пласт Каменни гъби, той е едно от най-интересните скални образувания от т.нар. Кърджалийски пирамиди, разположени по ридовете Каяджик и Чуката в Източните Родопи.",
        regionId: "BG-09",
        type: Type.NATURE,
        latitude: 41.6569386,
        longitude: 25.3991232,
        imageUrl: "assets/files/img/poi/kamenna_svatba.jpg",
        readMore: "https://www.bulgariatravel.org/article/details/222#map=6/42.750/25.380"
      },
      {
        id: 58,
        title: "Орлово око",
        address: "село Ягодина",
        description: "„Орлово око“ е платформа за наблюдение и туристическа атракция в Западните Родопи. Построена през 2009 година от туристическо дружество „Родопея“, село Ягодина, в близост до Ягодинската пещера. Платформата е построена на ръба на скала, разположена на 1563 метра надморска височина.",
        regionId: "BG-21",
        type: Type.NATURE,
        latitude: 41.6433343,
        longitude: 24.3389372,
        imageUrl: "assets/files/img/poi/orlovo_oko.jpg ",
        readMore: "https://zapadnirodopi.com/%D0%BE%D1%80%D0%BB%D0%BE%D0%B2%D0%BE-%D0%BE%D0%BA%D0%BE.html"
      }
    ];

    return {regions, facts, poi};
  }
}
