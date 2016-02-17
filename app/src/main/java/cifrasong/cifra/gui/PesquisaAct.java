package cifrasong.cifra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

import cifrasong.R;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.gui.MenuActivity;


public class PesquisaAct extends android.support.v7.app.AppCompatActivity {
    final CifraService negocio = new CifraService(PesquisaAct.this);

    Toolbar toolbar;

    static PesquisaCifraAsync pesquisa;
    static PesquisaArtistaAsync pesquisaArtista;

    public void onBackPressed(){
        Intent intent = new Intent(PesquisaAct.this,MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private static String[] artistas = {"henrique e juliano","jorge mateus","legiao urbana","charlie brown jr","banda malta","bruno e marrone","fernandinho","luan santana","marcos belutti","thalles roberto","lucas lucco","the beatles","anderson freire","victor leo","catolicas","aline barros","engenheiros do hawaii","gusttavo lima","eduardo costa","raul seixas","capital inicial","nando reis","natiruts","zeze di camargo e luciano","paula fernandes","roberto carlos","jota quest","mamonas assassinas","skank","chitaozinho e xororo","diante do trono","onze20","jads e jadson","armandinho","ze ramalho","fernando sorocaba","djavan","guns n roses","os paralamas do sucesso","cassia eller","pablo","los hermanos","cristiano araujo","o rappa","red hot chili peppers","lulu santos","tim maia","toque no altar","rosa de saron musicas","caetano veloso","chaves","harpa crista","natal","pitty","john legend","coldplay","ed sheeran","roupa nova","nirvana","corinhos evangelicos","nx zero","pink floyd","cazuza","milionario e jose rico","david quinlan","bruna karla","kleber lucas","metallica","fernanda brum","bruno mars","arctic monkeys","titas","bob marley","livres para adorar","jason mraz","oficina g3","clarice falcao","chico buarque","magic","detonautas","oasis","leandro e leonardo","nivea soares","amado batista","raimundos","joao bosco viniciusacustico","one direction","marisa monte","voz da verdade","chimarruts","revelacao","tiao carreiro e pardinho","andre valadao","gabriela rocha","led zeppelin","temas diversos","daniel","taylor swift","projota","kid abelha","temas de tv","maria gadu","quatro por um","foo fighters","tom jobim","padre marcelo rossi","ana carolina","sorriso maroto","raca negra","fagner","pearl jam","renascer praise","iron maiden","trazendo arca","tribalistas","cesar menotti e fabiano","joao mineiro e marciano","leonardo goncalves","anjos de resgate musicas","imagine dragons","guilherme e santiago","exaltasamba musicas","thaeme thiago","jack johnson","ac dc","sergio reis","seu jorge","vineyard","system of a down","adriana calcanhotto","john mayer","mc anitta","eli soares","christina perri","cpm 22","michel telo","avenged sevenfold","paramore","angra","trio parada dura","catedral","maroon 5","davi sacer","green day","bon jovi","matogrosso e mathias","paulo cesar baruk","whindersson nunes","munhoz mariano","leo nascimento","joao neto frederico","padre zezinho","fresno","roberta campos","zeca baleiro","demi lovato","almir sater","luiz gonzaga","ivete sangalo","adele","u2","temas de filmes","eyshila","soja","regis danese","gilberto gil","falamansa","frozen","edson e hudson","alceu valenca","leoni","john lennon","nenhum de nos","so pra contrariar","violetta","jorge vercillo","nickelback","leo magalhaes","christian e ralf","thiaguinho","frejat","queen","barao vermelho","joao carreiro capataz","extreme","linkin park","papas na lingua","justin bieber","ira","mc pikeno menor","lazaro","maneva","zeca pagodinho","avril lavigne","rodolfo abrantes","ls jack","cassiane","creedence clearwater revival","joao paulo e daniel","leonardo","michael jackson","catolica","elvis presley","chico rey e parana","one republic","hillsong united","scorpions","boyce avenue","damares","rita lee","forfun","pg","manoela gavassi","avicii","cidade negra","banda do mar","gian e giovanni","eric clapton","toquinho","loubet","heloisa rosa","eliana ribeiro","gorillaz","fabio jr","conrado aleksandro","milton nascimento","vanessa da mata","katy perry","sandy e junior musicas","bruninho davi","vida reluz","teodoro e sampaio","adhemar de campos","rick e renner","passenger","jorge ben jor","ludmila ferber","aerosmith","israel novaes","creed","pe fabio de melo","scracho","jotta a","tonico e tinoco","vinicius de moraes","ponto de equilibrio","cartola","turma do pagode","garota safada","soraya moraes","lana del rey","palavrantiga","reginaldo rossi","paul mccartney","daniela araujo","adoracao e adoradores","o teatro magico","jorge aragao","avioes do forro","comunidade catolica shalom","ventania","mariana valadao","instrucoes","daniel samuel","fundo de quintal","humberto ronaldo","slipknot","miley cyrus","beyonce","biquini cavadao","nani azevedo","ministerio adoracao vida","black sabbath","marquinhos gomes","renato russo","jose augusto","limao com mel","temas infantis","belchior","geraldo azevedo","ministerio pedras vivas","rihanna","matheus kauan","henrique diego","rbd","elis regina","giselli cristina","oriente","the eagles","pablo a voz romantica","tony allysson","marcelo camelo","lenine","dire straits","planta e raiz","gerson rufino","evanescence","belo","ministerio amor e adoracao","bryan adams","ultraje a rigor","pedra leticia","rio negro e solimoes","igreja crista maranata","sergio lopes","hugo pena e gabriel","the rolling stones","johnny cash","sam smith","david guetta","banda luxuria","maria cecilia rodolfo","ministerio koinonya de louvor","pimentas do reino","alcione","pantera","banda eva","bee gees","dlack","ramones","mallu magalhaes","adriana","bob dylan","ivo mozart","marquinho gomes","filhos do homem","strike","walmir alencar","pollo","israel rodolfo","matanza","james blunt","kings of leon","shirley carvalhaes","radiohead","zelia duncan","mc gui","andre e felipe","chiclete com banana","priscilla alcantara","os arrais","elton john","galinha pintadinha","maskavo","resgate","rpm","martinho da vila","art popular","3 doors down","malhacao","amy winehouse","cidadao quem","gonzaguinha","blink 182","the strokes","ministerio ipiranga","voz de muitas aguas","missionario shalom","disney","jamily","criolo doido","reacao em cadeia","audioslave","di paulo e paulino","deep purple","oswaldo montenegro","ana paula valadao","kansas","rael da rima","toca de assis","gospel","padre jonas","rem","fred gustavo","restart","adoniran barbosa","rebelde rbr","marcelo jeneci","renato teixeira","calcinha preta","gino e geno","naruto","the cranberries","paulinho moska","a banda mais bonita da cidade","vencedores por cristo","deigma marques","glee","samuel mariano","simple plan","kiss","ze henrique gabriel","novo som","jessie j","coral kemuel","paulo roberto","cicero","casa de davi","isreal kamakawiwoole","pharrel williams musicas","mattos nascimento musicas","the doors","the calling","pouca vogal","jesus culture","asaph borba","george henrique rodrigo","ministerio jovem","claudinho e buchecha","ratto","maria rita","alice in chains","raiz coral","dorgival dantas","rose nascimento","ministerio zoe","rufus wainwright","lynyrd skynyrd","pink","santa geracao","benito di paula","pedro paulo alex","tiago iorc","alexandre pires","ozzy osbourne","slash","5 seconds of summer","os serranos","comunidade shalom","colbie caillat","the police","klb","lauriete","hinos da harpa crista","thiago grulha","molejo","joao bosco","lorde","lourenco e lourival","jeito moleque","the fevers","guilherme arantes","simone","calvin harris","muse","celina borges","bezerra da silva","edu ribeiro cativeiro","nelson goncalves","jimi hendrix","arlindo cruz","beto guedes","j neto","rod stewart","goo goo dolls","mana","antonio cirilo","padre zeca","paulinho da viola","daft punk","pericles","michael w smith musicas","fernando mendes","marcos antonio","jammil e uma noites","erasmo carlos","bola de neve","lobao","vedder eddie","ariana grande","comunidade de nilopolis","lifehouse","ed motta","joao gilberto","mara lima","flavio venturini","daniel ludtke","luiz marenco","padre alessandro campos","esteban","ministerio sarando terra ferida","jake bugg","sungha jung","lady gaga","mato seco","os novos baianos","maria bethania","khorus","vanguart","edson gomes","panic at the disco","cesar e paulinho","megadeth","chico cesar","the kooks","gal costa","darvin","ze geraldo","os travessos","cristina mel","racionais mcs","dragon ball","laura souguellis","renato e seus bluecaps","30 seconds to mars","the cure","train","mumuzinho","the killers","judson oliveira","selena gomez","wando","fonte da vida","cypress hill","som louvor","kelly clarkson","banda dom","hinario adventista","grupo bom gosto","escalas","ellie goulding","marjorie estiano","bethel church","bastille","three days grace","fiduma e jeca","padre jonas abib","flavio jose","beth carvalho","silverchair","david bowie","offspring","hinos de futebol","psirico","gabriel pensador","vanessa rangel","hinos","vineyard music brasil","cantor cristao","gabriel diniz","mutantes","adoradores","dragon ball gt","austin mahone","armando filho","geraldo vandre","tenacious d","fred arrais","justin timberlake","mcfly","the pretty reckless","marcos goes","ministerio cristo vivo","banda fly","sublime","banda calypso","comunidade da zona sul","discopraise","babado novo","a ha","demonios da garoa","he is we","arianne","5 seco","skillet","marcela tais","roberta miranda","vander lee","cavaleiros do forro","the white stripes","marina peralta","george harrison","the lumineers","dunga","lu e robertinho","alda celia","ludwig van beethoven","pixote","sia","simon e garfunkel","ozeias de paula","frank sinatra","salomao do reggae","emmerson nogueira","asas livres","joao alexandre","gloria","salz band","davidson silva","emicida","claudia leitte","the smiths","danielle cristina","ivan lins","blitz","arnaldo antunes","dominguinhos","banda salmos","desejo de menina","meghan trainor","jonas brothers","arautos do rei","a great big world","chris duran","ton carfi","leandro leo","limp bizkit","marina lima","teixeirinha musicas","tie","jozyanne","simone simaria as coleguinhas","4 non blondes","fifth harmony","a turma do balao magico","lucas souza","harry potter","mariah carey","nevershoutnever","whitesnake","cesar oliveira rogerio melo","thiago brava","ferrugem","raquel mello","ronaldo bezerra","emilio santiago","celine dion","cachorro grande","arena louvor","the black keys","juninho cassimiro","cyndi lauper","imaginasamba","ouvir e crer","rodrigo amarante","comunidade evangelica de maringa","almir guineto","ministerio nova geracao","peninha","mastruz com leite","gilberto e gilmar","14 bis","brenno reis e marco viola","luiza possi","ritchie","neil young","dazaranha","san marino","willian nascimento","soweto","os cavaleiros do zodiaco musicas","ministerio de louvor iecvp","banda ego","rage against the machine","hevo 84","alanis morissette","alicia keys","shania twain","tracy chapman","elaine martins","velhas virgens","ze ricardo thiago","jeff buckley","clamor pelas nacoes","queens of the stone age","jason derulo","pato fu","phil collins","tomate","elaine de jesus","abba","mariana nolasco","agnaldo timoteo","tihuana","para nossa alegria","sandra de sa","ney matogrosso","dorival caymmi","clara nunes","elba ramalho","felipe valente","marco aurelio","hugo e tiago","roxette","rodox","grupo logos","kleo dibah rafael","edy brito e samuel","foster the people","kelly patricia","boka loka","diego fernandes","stevie wonder","stone sour","aliados","dragonball z","os incriveis","dream theater","john frusciante","secos molhados","hillsong brasil","noel rosa musicas","agnus dei","harmonia do samba","altermar dutra","araketu","vicente nery","rodrigo soeiro","ministerio sao miguel arcanjo","banda magnificos","marcelo d2","shakira","paulo ricardo","michael buble","suricato","skid row","gabriel gava","supercombo","liu e leo","van halen","europe","of monsters and men","louis armstrong","henrique cerqueira","bonde da stronda","lorena chaves","ze felipe","mc pedrinho","diogo nogueira","marilyn manson","odair jose","gui rebustini","pixinguinha","bullet for my valentine","fruto sagrado","pedro henrique fernando","grupo nosso sentimento","beirut","planet hemp","lairton","eminem","pamela","mumford sons","leo jaime","backstreet boys","my chemical romance","incubus","campanha da fraternidade","os nonatos","tche garotos","plain white ts","damien rice","padre reginaldo manzotti","lo borges","katinguele","chris brown","eugenio jorge","saulo fernandes","marcus salles","snow patrol","juanes","kim","ministerio avivah","waldir azevedo","anselmo ralph","rui veloso","daniel souza","voices","lionel richie","the animals","testemunhas de jeova","sa guarabyra","keane","reginaldo veloso","filipe ret","igreja batista da lagoinha","flavinho","alan jackson","melk villar","os monarcas","rafaela pinho","hunger games","tayrone cigano","counting crows","robson biollo","alan aladin","asa de aguia","seu cuca","tnt","marcos witt","luciano claw","ben harper","pedro gazola","piratas do caribe","joao nogueira","nechivile","sergio saas","johann sebastian bach","a day to remember","mr big","pena branca e xavantinho","elias silva","hebrom ministerio fortaleza de adoracao","mc guime","leo canhoto e robertinho","janis joplin","stone temple pilots","danni carlos","britney spears","birdy","hinos adventistas","xuxa","carlinhos felix","nico","fall out boy","ministerio alem do veu","apanhador so","baden powell","isabella taviani","luiz melodia","thiago brado","grupo clareou","vera loca","hoobastank","dominio publico","paulo diniz","renato vianna","pokemon","chuck berry","whitney houston","enrique iglesias","megafone","mensagem brasil","igreja batista nova jerusalem","rush","cat stevens","phillip phillips","kari jobe","gasparzinho","mr probz","hillsong music australia","chicabana","barto galeno","felipe e falcao","marcelo aguiar","los lobos","the who","aline barros e fernandinho","circulado de fulo","swedish house mafia","reinaldo","athaide e alexandre","suellen lima","luiz de carvalho","r5","lenny kravitz","dead fish","seether","wesley safadao","bruce dickinson","claudio zoli","alter bridge","guitar hero 2","lex skate rock","hori","agepe","hinario ccb no 5","kuase nada","cone crew diretoria","mario bros","rammstein","mayck lyan","sleeping with sirens","os levitas","cassiane e jairinho","tania mara","survivor","luis miguel","roberta sa","barrerito","nightwish","humberto gessinger","vivendo do ocio","the fray","cancao nova","zelda","cleiton e camargo","gisele cristina","palavra cantada","christina aguilera","sergio dallorto","jonas vilar","mc daleste","marcos castro","edu ribeiro","valeska popozuda","ronaldo santos","bring me the horizon","paulo sergio","hora de aventura","jeferson pillar","cheiro de amor","robbie williams","prisma brasil","nintendo","jairo bonfim","ministerio uncao de deus","high school musical","marcos valle","gilmar britto","nelson cavaquinho","santorine","roy orbison","waguinho","gotye","the neighbourhood","xutos e pontapes","inimigos da hp","padre antonio maria","antonio marcos","james morrison","fogo no pe","duduca e dalvan","lady antebellum","mauricio manieri","sum 41","black eyed peas","bonde do brasil","bide ou balde","sensacao","nazareth","daniela mercury","joe satriani","dama","sozo","tove lo","daniel alencar","smashing pumpkins","radicais livres","tribo de jah","selvagens procura de lei","padre cleidimar moreira","tatiana costa","os originais do samba","ziza fernandes","tequila baby","laura pausini","musica natalina","alex alex","iahweh","comunhao e adoracao","pedro valenca","belmonte e amarai","phill veras","ze marco e adriano","black label society","journey","fatima souza","pregador luo","sandy","fernando iglesias","sergio sampaio","ministerio fonte da vida de adoracao","bb king","gatinha manhosa","america","franz ferdinand","pod","acusticos e valvulados","sambo","ne yo","salette ferreira","grupo tradicao","hugo gabriel","slayer","the wanted","black veil brides","camisa de venus","cine","vanilda bordieri","cascavelletes","massacration musicas","ministerio kyrios","rodriguinho","haikaiss","juliana de paula","igreja adventista do setimo dia","gisele nascimento","goiano paranaense","carlinhos brown","misfits","moraes moreira","zakk wylde","vaughan stevie ray","santana","parabens pra voce","anjos do hanngar","grupo revelacao","novo tom","banda louvor gloria"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pesquisa);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PesquisaAct.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,artistas);

        final Button pesquisar = (Button) findViewById(R.id.pesquisar);
        final EditText nomeMusica = (EditText) findViewById(R.id.nomeMusica);
        final AutoCompleteTextView nomeArtista = (AutoCompleteTextView) findViewById(R.id.nomeArtista);


        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        final TextView texto = (TextView) findViewById(R.id.texto);

        nomeArtista.setAdapter(adapter);

        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sufixo = tiraChar(nomeArtista.getText().toString()) + "/" + tiraChar(nomeMusica.getText().toString());
                ExibeCifraPesquisaAct.cifraArtista = nomeArtista.getText().toString();
                ExibeCifraPesquisaAct.cifraNome = nomeMusica.getText().toString();

                pesquisa = new PesquisaCifraAsync(PesquisaAct.this, progress, texto, negocio.montaLink(sufixo), pesquisar);

                pesquisaArtista = new PesquisaArtistaAsync(PesquisaAct.this, progress, texto, negocio.montaLink(nomeArtista.getText().toString()), pesquisar, nomeArtista.getText().toString());

                String artista = nomeArtista.getText().toString();
                String musica = nomeMusica.getText().toString();

                try {
                    if (musica.length() == 0 && artista.length() != 0) {
                        progress.setVisibility(View.VISIBLE);
                        pesquisaArtista.execute();
                        pesquisar.setClickable(false);
                        Toast.makeText(PesquisaAct.this, "Aguarde a nossa Busca.", Toast.LENGTH_SHORT).show();
                    } else if (musica.length() == 0) {
                        Toast.makeText(PesquisaAct.this, "Digite um musica para a busca.", Toast.LENGTH_SHORT).show();
                    } else if (artista.length() == 0) {
                        Toast.makeText(PesquisaAct.this, "Digite um artista para a busca.", Toast.LENGTH_SHORT).show();
                    } else {
                        pesquisa.execute();
                        pesquisar.setClickable(false);
                        progress.setVisibility(View.VISIBLE);
                        Toast.makeText(PesquisaAct.this, "Aguarde a nossa Busca.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PesquisaAct.this, "Verifique sua conexão com a Internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String tiraChar (String input){
        String text;
        text = Normalizer.normalize(input, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        text = text.toLowerCase();
        return text;
    }
}