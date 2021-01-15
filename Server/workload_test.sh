chmod +x ./send_data.sh
total=$1;
for ((n=0;n<total;n++)); do	
    ./send_data.sh $n; 
done

