<?php
    include 'connect_db.php';
    function make_seed()
{
  list($usec, $sec) = explode(' ', microtime());
  return (float) $sec + ((float) $usec * 100000);
}

    $filep = file_get_contents("test.json");
    
    $topic_cluster = json_decode($filep,true);
    
      
    if($_POST['action'] == 'init'){
         //var_dump($topic_cluster);
         $keys = array_keys($topic_cluster);
         foreach($keys as $key){ echo '<option value="'.$key.'">'.$key.'</option>'; }
     
    }
    
    if($_POST['action'] == 'getVizData'){
    
       echo '{';
       echo '"name":"561",';
       echo '"children":[';
       
       $keys = array_keys($topic_cluster);
       $numCluster = count($keys);
       $i=0;
       foreach($keys as $key){ 
        echo '{"name":"'.$key.'",';
        echo '"children":{';
        
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
    }
      
      
    if($_POST['action'] == 'getInfo') {
       
       $arr = $topic_cluster[$_POST['cluster']]['Document'];
       echo "<h3> Words in Cluster</h3>";
       echo '<p class=\'Words\'>';
       foreach($arr as $key => $value ){
        echo $value['Word'].'&nbsp;&nbsp;&nbsp;';
       }
       echo '</p>';
       
       $arr = $topic_cluster[$_POST['cluster']]['Elements'];
       echo "<h3> Words in Cluster</h3>";
       
       foreach($arr as $key => $value ){
       echo '<p class=\'Tweets\'>';
        echo $value['Tweet'].'';
        echo '</p>';
       }
       
       
    }
    
    if($_POST['action'] == 'getData') {
    
    $newsFile = file_get_contents("news.json");
    $newsJson = json_decode($newsFile,true);
    $newsClusters =  array_keys( $newsJson);
     
    $arr = $topic_cluster[$_POST['cluster']]['Document'];
    
    $words = array();
    
    foreach($arr as $key => $value ){
        array_push($words,$value['Word']);
        
    }
     // $words = ['Ranbaxy','Recall'];

      foreach($words as $word){
           $searchterm[] = " TweetText LIKE '%$word%'";
      } 
    $rs = mysql_query("SELECT DATE_FORMAT(CreationDate,'%m-%d'),Count(*) FROM tweet WHERE " .implode(' OR ', $searchterm)." GROUP BY Date(CreationDate)");
    
   $result = '[ [ \'Time\', \'Tweeter\'';

    $check = NULL;
    foreach($newsClusters as $key => $value){
       if($value == $_POST['cluster']){
       $result .= ',\'News\'';
       $check = $key;
       }
    }
    $result.= '],';
    
    while($row = mysql_fetch_array($rs)){
        $result .='[\''.$row[0].'\','.$row[1];
        if(!is_null($check)){
          $default_value = 0;
         // var_dump($newsJson[$_POST['cluster']]['Document']);
          foreach($newsJson[$_POST['cluster']]['Document'] as $key => $value ){
              
             if($value['TIME'] == $row[0]){
                $default_value = $value['Frequency'];
             }
        }
        $result .= ','.$default_value;
        
        
        }
       $result .= "],";    
    }
    
    $result = substr_replace($result, "]", -1);

    
    echo $result;

    }
   
?>
