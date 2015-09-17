package org.codehaus.plexus.archiver.gzip;

/**
 *
 * Copyright 2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.ArchiverException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static org.codehaus.plexus.archiver.util.Streams.*;

/**
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Revision$ $Date$
 */
public class GZipUnArchiver
    extends AbstractUnArchiver
{
    private static final String OPERATION_GZIP = "gzip";


    public GZipUnArchiver()
    {
    }

    public GZipUnArchiver( File sourceFile )
    {
        super( sourceFile );
    }

    protected void execute()
        throws ArchiverException
    {
        if ( getSourceFile().lastModified() > getDestFile().lastModified() )
        {
            getLogger().info(
                "Expanding " + getSourceFile().getAbsolutePath() + " to " + getDestFile().getAbsolutePath() );

            copyFully( getGzipInputStream( fileInputStream( getSourceFile(), OPERATION_GZIP ) ),
                       fileOutputStream( getDestFile(), OPERATION_GZIP ), OPERATION_GZIP );
        }
    }

    private InputStream getGzipInputStream( FileInputStream in )
        throws ArchiverException
    {
        try
        {
            return new BufferedInputStream( new GZIPInputStream( in ));
        }
        catch ( IOException e )
        {
            throw new ArchiverException( "Problem creating GZIP input stream", e );
        }
    }

    protected void execute( String path, File outputDirectory )
    {
        throw new UnsupportedOperationException( "Targeted extraction not supported in GZIP format." );
    }
}
