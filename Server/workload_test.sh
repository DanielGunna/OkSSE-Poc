chmod +x ./send_data.sh
total=$1;
for ((n=0;n<total;n++)); do	
    ./send_data.sh $n; 
    if [[ $2 -ne 0 ]] ; then
    	sleep $2;
    fi
done

