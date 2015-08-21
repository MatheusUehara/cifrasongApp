package cifrasong.usuario.gui;

import com.getbase.floatingactionbutton.FloatingActionButton;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.view.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.view.MotionEvent;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cifrasong.R;
import cifrasong.cifra.gui.PesquisaAct;
import cifrasong.usuario.dominio.Session;
import cifrasong.util.material.SlidingTabLayout;

public class MenuActivity extends android.support.v7.app.AppCompatActivity {

    public void onQuit(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);

        alertDialogBuilder.setTitle("Sair");
        alertDialogBuilder.setMessage("Deseja sair do aplicativo ?");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Session.setUsuarioLogado(null);
                Intent i = new Intent(MenuActivity.this, LoginAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void onBackPressed(){
        onQuit();
    }


    // Declaring Your View and Variables
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Minhas Cifras"," Cifras Favoritas"};
    int Numboftabs =2;
    private static final int REQUEST_PICTURE = 1000;
    private static Bitmap fotoCifra;
    private File imageFile;

    String TITLES1[] = {"Galeria de Cifras","Sobre","Sair"};
    int ICONS[] = {R.drawable.ic_action_galeria,R.drawable.ic_action_sobre,R.drawable.ic_action_sair};

    String NAME = Session.getUsuarioLogado().getLogin();
    String EMAIL = Session.getUsuarioLogado().getEmail();
    int PROFILE = R.drawable.ic_launcher;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

//############################## IMAGENS DA CIFRA ###########################

// Obtem o local onde as fotos sao armazenadas na memoria externa do dispositivo
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/Cifrasong");

// Define o local completo onde a foto sera armazenada (diretorio + arquivo)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date data = new Date();
        Calendar  cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);

        this.imageFile = new File(picsDir, "CifraSong-"+data_completa+".jpg");


// ##################### Criacao do toolbar

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

//####################### Sliding tabs adapter ####################

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

//#######################  FIM Sliding tabs adapter ####################


//############# parte do floating action buttom #######################

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.branco));


        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (MenuActivity.this,PesquisaAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "Clicou em Tirar Foto", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Indica na intent o local onde a foto tirada deve ser armazenada
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                // Abre a aplicacao de camera
                startActivityForResult(i, REQUEST_PICTURE);
            }
        });

//############# fim floating action buttom #######################


//######################### Nav Drawer #############

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new DrawerAdapter(TITLES1,ICONS,NAME,EMAIL,PROFILE,this);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,menu_header view name, menu_header view email and context for adapter
        // and menu_header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        };

        // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State


        final GestureDetector mGestureDetector = new GestureDetector(MenuActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    int position = recyclerView.getChildPosition(child);

                    if (position == 1){
                        Toast.makeText(MenuActivity.this,"Clicou na Galeria de Cifras", Toast.LENGTH_SHORT).show();
                        // Adicionar código de call da galeria.
                        // Adicionar código de call da galeria.
                        // Adicionar código de call da galeria.
                        // Adicionar código de call da galeria.
                        // Adicionar código de call da galeria.
                    }
                    else if (position == 2 ){
                        Intent i = new Intent(MenuActivity.this,SobreAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else if (position ==3){
                        onQuit();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

        });
//################## Fim nav Drawer #############
    }
    /**
     * Metodo chamado quando a aplicao nativa da camera e finalizada
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Verifica o codigo de requisicao e se o resultado e OK (outro resultado indica que
        // o usuario cancelou a tirada da foto)
        if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {

            FileInputStream fis = null;

            try {
                try {
                    // Cria um FileInputStream para ler a foto tirada pela c?mera
                    fis = new FileInputStream(imageFile);

                    // Converte a stream em um objeto Bitmap
                    Bitmap picture = BitmapFactory.decodeStream(fis);

                    fotoCifra = picture;

                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}