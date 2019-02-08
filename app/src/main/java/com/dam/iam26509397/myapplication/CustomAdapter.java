package com.dam.iam26509397.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "test";

    private List<String> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView data;
        //  private final TextView tempe;
        //private final ImageView imatge;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            data = (TextView) v.findViewById(R.id.firstLine);
            // tempe = (TextView) v.findViewById(R.id.secondLine);
            //thirdLine=(TextView) v.findViewById(R.id.thirdLine);
            //imatge = (ImageView) v.findViewById(R.id.icon);


        }

        public TextView getTextView() {
            // return textView;
            return null;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<String> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_layout, viewGroup, false);

        return new ViewHolder(v);
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        //viewHolder.getTextView().setText(mDataSet[position]);
        // final Temp name = mDataSet.get(position);

        // TODO: 27/01/19 millorar aquesta formatació, webservice que torni bandera segons codi pais, etc..
        // String temp=name.getTempe();
        // String vent=name.getWind();
        //temp=String.format("%s %s", temp,".00");
        //if (vent.length()==1) vent=vent.concat(".00");
        //if (vent.length()==3) vent=vent.concat("0");

        final String data=mDataSet.get(position);


        holder.data.setText(String.valueOf(data) );
        holder.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executarem arxiu...
                try {
                    PlaySound(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void PlaySound (int position) throws IOException {
        final int[] loaded = {0};
        int soundID;
        /*SoundPool pool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded[0] +=1;
            }

        });
      if (loaded[0] ==1) {
            pool.play(soundID, 1, 1, 1, 0, 1f);
        }
*/
        final MediaPlayer myMediaPlayer = new MediaPlayer();
        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        myMediaPlayer.setDataSource(mDataSet.get(position));

        if (!myMediaPlayer.isPlaying()) {
            myMediaPlayer.prepareAsync();
            myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "Starting music");
                    // mpReady = true;
                    myMediaPlayer.start();
                }
            });
        }
        //soundID = pool.load(this, mDataSet.get(position),1)
        //R.raw.pingpongsound, 1);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataSet.size();
    }
}

