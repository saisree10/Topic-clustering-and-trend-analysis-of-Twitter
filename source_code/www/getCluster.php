 <?PHP     $filep = file_get_contents("test.json");
    
    $topic_cluster = json_decode($filep,true);
    
       echo '{';
       echo '"name":"561",';
       echo '"children":[';
       
       $keys = array_keys($topic_cluster);
       $numCluster = count($keys);
       $i=0;
       foreach($keys as $key){ 
        echo '{"name":"'.$key.'",';
        echo '"children":[';
        
        $arr = $topic_cluster[$key]['Document'];
        $NumWords = count($arr);
        $j = 0;
        foreach($arr as $key => $value){
        
        echo '{"name":"'.$value['Word'].'","size":"'.$value['Frequency'].'"}';
        if(++$j != $NumWords)
        echo ',';
        }
        
        echo ']}';
        if(++$i != $numCluster)
        echo ',';
       
       }
       echo ']';
       echo '}';
    
    ?>