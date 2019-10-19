# VEHICLE-GEO-DISPERSED-BIG-DATA-PROCESSING

Processing Geo Dispersed Big Data”, is a big data project which provides vehicle
information from large collections of data that is huge in size and can grow exponentially with
time. This information is generated, after accepting query such as vehicle number, color from
the user, and generating the end result by referring different data-sets. Since the data is
geographically dispersed it is a huge challenge to process and handle such request using
traditional data management tools.

Big Data in general is suitable for dealing with data-sets of high volume, variety,
velocity, variability. It supports the full spectrum of data types, protocols and integration
scenarios to speed up and simplify data wranglings. The emphasize is on moving the
computation to the data rather than the other way around and hence provides the benefits of
improving customer service, avoiding processing delays, better operational efficiency and
early identification of risks.

In this internship project, the proposed work allows the user to work with different
data-sets and explores the challenges in designing a system for processing big data that is
geographically distributed. Moreover the proposed work also focuses on the advantages of
Big Data such as creation of a robust data management platform to gain high value insights
of vehicle information across disparate data sources and types.

# Map Reduce

MapReduce is a programming framework that abstracts the complexity of parallel
applications by partitioning and scattering data sets across hundreds or thousands of
machines, and by bringing computation and data closer. The Figure 3.1, shows the
MapReduce architectural diagram [9]. The Map and Reduce phases are handled by the
programmer, whereas the Shuffle phase is created while the job is being carried out. The
input data is split into smaller pieces called chunks, that normally have a size of 64 MB.
The data is a serialized and distributed across machines that compose the Distributed File System
(DFS).

When running an application, the job is split by the master into several Map and Reduce
tasks; following this, it assigns tasks to workers that then run each processing stage. The
machine that is given a Map task, handles a Map function and emits key/value pairs as
intermediate results that are temporarily stored in the worker disks. The execution model
creates a computational barrier, which allows tasks to be synchronized between the Map
and Reduce. A Reduce task does not start its processing until all the Map tasks have been
completed.

A hash function is applied to the intermediate data produced during the Map phase
to determine which keys will compose a Reduce task. All the pairs combined with these
keys are transferred to one machine, during the Shuffle, so that they can be processed by
a Reduce task. After a reduction function has been applied to this data, a new key/value
pair is issued. The result is then stored in the distributed file system and thus can be made
available to the client who submitted the job.

The Shuffle phase consists of two stages: one performed on the machine that
processes the Map task, which sorts the keys and serializes the data. The other is
performed after the intermediate data has been sent to the reducer machine, which
arranges the received data to allow the keys to be properly grouped and then runs the
Reduce task. The Hadoop implements the Combiner function to save bandwidth. The
Combiner is a pre-processing of intermediate keys into memory of workers during the
map phase to form the output for input data in the Reduce phase.

# Step to Execute

This is a hadoop application which will take a vehical info as dataset.

Before running make sure that "tempDataset" and "finalResult" is already present when you run below command

hdfs dfs -ls

if those file exist use below command to remove folder

hdfs dfs -rmr tempDataset
