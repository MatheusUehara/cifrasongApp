package cifrasong.cifra.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import cifrasong.R;
import cifrasong.usuario.gui.MenuActivity;

public class GaleriaAct extends android.support.v7.app.AppCompatActivity {

    AsyncTaskLoadFiles myAsyncTaskLoadFiles;

    static final int ID_JPGDIALOG = 0;
    ImageView jpgdialigImage;
    File jpgdialigFile;
    final int DIALOG_IMAGE_WIDTH = 500;
    final int DIALOG_IMAGE_HEIGHT = 350;


    public class AsyncTaskLoadFiles extends AsyncTask<Void, String, Void> {

        File targetDirector;
        ImageAdapter myTaskAdapter;

        public AsyncTaskLoadFiles(ImageAdapter adapter) {
            myTaskAdapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            String ExternalStorageDirectoryPath = Environment
                    .getExternalStorageDirectory().getAbsolutePath();

            String targetPath = ExternalStorageDirectoryPath + "/Pictures/Cifrasong/";
            targetDirector = new File(targetPath);
            myTaskAdapter.clear();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //open jpg only
            File[] files = targetDirector.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name)
                {
                    return (name.endsWith(".jpg")||name.endsWith(".JPG"));
                }
            });
            //File[] files = targetDirector.listFiles();

            Arrays.sort(files);
            for (File file : files) {
                publishProgress(file.getAbsolutePath());
                if (isCancelled()) break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            myTaskAdapter.add(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            myTaskAdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        ArrayList<String> itemList = new ArrayList<String>();

        public ImageAdapter(Context c) {
            mContext = c;
        }

        void add(String path) {
            itemList.add(path);
        }

        void clear() {
            itemList.clear();
        }

        void remove(int index){
            itemList.remove(index);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        //getView load bitmap in AsyncTask
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some
                // attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);

                convertView = imageView;

                holder = new ViewHolder();
                holder.image = imageView;
                holder.position = position;
                convertView.setTag(holder);
            } else {
                //imageView = (ImageView) convertView;
                holder = (ViewHolder) convertView.getTag();
                ((ImageView)convertView).setImageBitmap(null);
            }

            //Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220, 220);
            // Using an AsyncTask to load the slow images in a background thread
            new AsyncTask<ViewHolder, Void, Bitmap>() {
                private ViewHolder v;

                @Override
                protected Bitmap doInBackground(ViewHolder... params) {

                    Bitmap bm = null;

                    boolean haveThumbNail = false;

                    try {
                        ExifInterface exifInterface =
                                new ExifInterface(itemList.get(position));
                        if(exifInterface.hasThumbnail()){
                            byte[] thumbnail = exifInterface.getThumbnail();
                            bm = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
                        }
                        haveThumbNail = true;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if(!haveThumbNail){
                        bm = decodeSampledBitmapFromUri(
                                itemList.get(position), 220, 220);
                    }

                    v = params[0];
                    return bm;
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);

                    v.image.setImageBitmap(result);

                }
            }.execute(holder);
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            int position;
        }

    }

    public void onBackPressed(){
        Intent intent = new Intent(GaleriaAct.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    Toolbar toolbar;
    ImageAdapter myImageAdapter;

    private static final int REQUEST_PICTURE = 1000;
    private static Bitmap fotoCifra;
    private File imageFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        // ##################### Criacao do toolbar e do botão de voltar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GaleriaAct.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        //############################## IMAGENS DA CIFRA ###########################

// Obtem o local onde as fotos sao armazenadas na memoria externa do dispositivo
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+"/Cifrasong");

// Define o local completo onde a foto sera armazenada (diretorio + arquivo)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);

        this.imageFile = new File(picsDir, "CifraSong-"+data_completa+".jpg");


        final GridView gridview = (GridView) findViewById(R.id.gridview);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);

        myAsyncTaskLoadFiles = new AsyncTaskLoadFiles(myImageAdapter);
        myAsyncTaskLoadFiles.execute();

        gridview.setOnItemClickListener(myOnItemClickListener);


        //################### FLOATING ACtiON BUTTON

        findViewById(R.id.pink_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel the previous running task, if exist.
                myAsyncTaskLoadFiles.cancel(true);

                //new another ImageAdapter, to prevent the adapter have
                //mixed files
                myImageAdapter = new ImageAdapter(GaleriaAct.this);
                gridview.setAdapter(myImageAdapter);
                myAsyncTaskLoadFiles = new AsyncTaskLoadFiles(myImageAdapter);
                myAsyncTaskLoadFiles.execute();
            }
        });
    }

    OnItemClickListener myOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            String path = (String) parent.getItemAtPosition(position);

            //Open dialog to show jpg
            jpgdialigFile = new File(path);

            showDialog(ID_JPGDIALOG);

        }
    };

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        final Dialog jpgDialog = new Dialog(this);
        switch(id){
            case ID_JPGDIALOG:

                jpgDialog.setContentView(R.layout.jpgdialog);
                jpgdialigImage = (ImageView)jpgDialog.findViewById(R.id.image);
                //jpgdialigText = (TextView)jpgDialog.findViewById(R.id.textpath);

                Button okDialogButton = (Button)jpgDialog.findViewById(R.id.okdialogbutton);
                okDialogButton.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        jpgDialog.dismiss();
                    }});
                break;

            default:
                break;
        }

        return jpgDialog;
    }

    @Override
    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch(id){
            case ID_JPGDIALOG:
                //jpgdialigText.setText(jpgdialigFile.getPath());
                //Bitmap bm = BitmapFactory.decodeFile(jpgdialigFile.getPath());
                Bitmap bm = decodeSampledBitmapFromUri(jpgdialigFile.getPath(),
                        DIALOG_IMAGE_WIDTH, DIALOG_IMAGE_HEIGHT);
                jpgdialigImage.setImageBitmap(bm);

                break;

            default:
                break;
        }
    }

    private Bitmap decodeSampledBitmapFromUri(
            String path, int reqWidth, int reqHeight) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height/(float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_galeria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tirarFoto) {
            try {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Indica na intent o local onde a foto tirada deve ser armazenada
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                // Abre a aplicacao de camera
                startActivityForResult(i, REQUEST_PICTURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}